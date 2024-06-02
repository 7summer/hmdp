package com.hmdp.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.constant.RedisConstants;
import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.hmdp.utils.RedisWork;
import com.hmdp.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {
    @Autowired
    private RedisWork redisWork;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ISeckillVoucherService seckillVoucherService;

    @Autowired
    private RedissonClient redissonClient;


    private static final RedisScript<Long> redisScript;
    static {
        // 得到lua脚本
        // 以下脚本执行：优惠券是否存在、优惠券是否开始秒杀、优惠券是否结束秒杀、库存是否足够、一人一单
        // 如果以上条件满足，就执行：扣缓存、用户下单（往Set添加用户）、添加订单消息到消息队列
        redisScript = RedisScript.of(new ClassPathResource("/lua/seckillvoucher.lua"), Long.class);
    }

    /**
     * 代理类
     * seckillVoucher方法中有代理类的创建
     */
    private  IVoucherOrderService service;

    /**
     * 订单处理类
     * 从消息队列中取订单信息
     * 更新秒杀券的库存
     * 向数据库中插入订单信息
     */
    private class VoucherOrderHandler implements Runnable {
        @Override
        public void run() {
            String key = RedisConstants.VOUCHER_ORDER_MESSAGE_LIST_KEY;

            // 处理订单消息队列
            while (true) {
                try {
                    StreamOperations<String, Object, Object> operations = stringRedisTemplate.opsForStream();

                    // 从消息队列中读一条订单消息
                    List<MapRecord<String, Object, Object>> list = operations.read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2L)),
                            StreamOffset.create(key, ReadOffset.lastConsumed()));
                    if (list == null || list.isEmpty()) {
                        continue;
                    }

                    // 解析消息中的订单信息
                    MapRecord<String, Object, Object> orderRecord = list.get(0);

                    // 将map映射为
                    Map<Object, Object> orderValue = orderRecord.getValue();
                    VoucherOrder voucherOrder = getVoucherOrder(orderValue);

                    // 下单
                    handleCreateOrder(voucherOrder);

                    // ack确定
                    operations.acknowledge(key, "g1", orderRecord.getId());
                } catch (Exception e) {
                    // 读pending-list，继续处理订单
                    throwExceprionHandle();
                }
            }
        }

        /**
         * 抛出异常后的处理
         * 读pending-list，继续处理订单
         */
        private void throwExceprionHandle() {
            String key = RedisConstants.VOUCHER_ORDER_MESSAGE_LIST_KEY;

            while (true) {
                try {
                    StreamOperations<String, Object, Object> operations = stringRedisTemplate.opsForStream();

                    // 从pending-list中读一条订单消息
                    List<MapRecord<String, Object, Object>> list = operations.read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1),
                            StreamOffset.create(key, ReadOffset.from("0")));
                    if (list == null || list.isEmpty()) {
                        break;
                    }

                    // 解析消息中的订单信息
                    MapRecord<String, Object, Object> orderRecord = list.get(0);

                    // 将map映射为
                    Map<Object, Object> orderValue = orderRecord.getValue();
                    VoucherOrder voucherOrder = getVoucherOrder(orderValue);

                    // 下单
                    handleCreateOrder(voucherOrder);

                    // ack确定
                    operations.acknowledge(key, "g1", orderRecord.getId());
                } catch (Exception e) {
                    continue;
                }
            }
        }

        /**
         * 创建订单
         * @param voucherOrder
         */
        private void handleCreateOrder(VoucherOrder voucherOrder) {
            if (service == null){
                System.out.println("service is null");
                throw new RuntimeException();
            }

            String distributedLockKey = RedisConstants.DISTRIBUTED_LOCK_KEY
                    + "voucher:" + voucherOrder.getVoucherId()
                    + ":user:" + voucherOrder.getUserId();
            RLock lock = redissonClient.getLock(distributedLockKey);

            try {
                if (lock.tryLock(0L, RedisConstants.DISTRIBUTED_LOCK_TTL, TimeUnit.MINUTES)) {
                    service.createVoucherOrder(voucherOrder);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }

        /**
         * 将Map映射为VoucherOrder
         * @param orderMap
         * @return
         */
        private VoucherOrder getVoucherOrder(Map<Object, Object> orderMap) {
            VoucherOrder voucherOrder = new VoucherOrder();

            voucherOrder.setId(Long.valueOf((String) orderMap.get("orderId")));
            voucherOrder.setVoucherId(Long.valueOf((String) orderMap.get("voucherId")));
            voucherOrder.setUserId(Long.valueOf((String) orderMap.get("userId")));

            return voucherOrder;
        }
    }

    /**
     * 单线程池
     */
    public static final ExecutorService singleService = Executors.newSingleThreadExecutor();

    /**
     * 类加载完成后，提交线程任务
     * 不断从消息队列中取订单信息
     * 处理这些订单信息
     */
    @PostConstruct
    public void init() {
        singleService.submit(new VoucherOrderHandler());
    }

    /**
     * 秒杀下单
     * @param voucherId
     * @return
     */
    @Override
    public Result seckillVoucher(Long voucherId) {
        long userId = UserHolder.getUser().getId();

        // 优惠券key
        String seckillVoucherKey = RedisConstants.CACHE_SECKILL_KEY + voucherId;
        // 已购优惠券用户列表key
        String seckillVoucherUserSetKey = RedisConstants.BOUGH_SECKILL_USER_Set_KEY + voucherId;
        // 订单消息队列Key
        String createOrderMessageQueueKey = RedisConstants.VOUCHER_ORDER_MESSAGE_LIST_KEY;

        // 代理类创建
        this.service = (IVoucherOrderService) AopContext.currentProxy();
        // 执行lua脚本
        // 优惠券是否存在、优惠券是否开始秒杀、优惠券是否结束秒杀、库存是否足够、一人一单
        // 满足以上要求执行：扣库存、用户下单（往Set添加用户）、将订单信息添加到消息队列
        // 订单信息添加到消息队列后，会有一个线程帮助我们处理
        Long execute = stringRedisTemplate.execute(redisScript,
                Arrays.asList(seckillVoucherKey, seckillVoucherUserSetKey, createOrderMessageQueueKey),
                String.valueOf(userId),
                String.valueOf(voucherId),
                String.valueOf(redisWork.nextId(RedisConstants.INCR_VOUCHER_ORDER_ID_KEY)));
        if (execute == null) {
            return Result.fail("秒杀失败");
        }

        return Result.ok("秒杀成功");
    }

    /**
     * 创建订单
     * @param voucherOrder
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean createVoucherOrder(VoucherOrder voucherOrder) {
        Long voucherId = voucherOrder.getVoucherId();

        // 扣减库存
        UpdateWrapper<SeckillVoucher> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("stock = stock - 1");
        updateWrapper.eq("voucher_id", voucherId);
        // 只要库存量大于0就能更新
        updateWrapper.gt("stock", 0);
        if (!seckillVoucherService.update(updateWrapper)) {
            return false;
        }

        // 保存订单信息
        return this.save(voucherOrder);
    }
}
