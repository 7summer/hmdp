package com.hmdp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.constant.RedisConstants;
import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.Voucher;
import com.hmdp.mapper.VoucherMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据商铺id查询优惠券
     * @param shopId
     * @return
     */
    @Override
    public Result queryVoucherOfShop(Long shopId) {
        // 查询优惠券信息
        List<Voucher> vouchers = getBaseMapper().queryVoucherOfShop(shopId);
        // 返回结果
        return Result.ok(vouchers);
    }

    /**
     * 添加秒杀券
     * @param voucher
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSeckillVoucher(Voucher voucher) {
        // 保存优惠券
        save(voucher);
        // 保存秒杀信息
        SeckillVoucher seckillVoucher = new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setStock(voucher.getStock());
        seckillVoucher.setBeginTime(voucher.getBeginTime());
        seckillVoucher.setEndTime(voucher.getEndTime());
        seckillVoucherService.save(seckillVoucher);

        // 将秒杀券存入缓存
        Map<String, String> seckillVoucherMap = getSeckillVoucherMap(seckillVoucher);
        // 优惠券key
        String seckillVoucherKey = RedisConstants.CACHE_SECKILL_KEY + voucher.getId();
        // 已购优惠券用户列表key
        String seckillVoucherUserSetKey = RedisConstants.BOUGH_SECKILL_USER_Set_KEY + voucher.getId();

        // 将秒杀券信息存入缓存
        stringRedisTemplate.opsForHash().putAll(seckillVoucherKey, seckillVoucherMap);
        // 将已购优惠券的用户信息存入
        stringRedisTemplate.opsForSet().add(seckillVoucherUserSetKey, String.valueOf(0));
        // 设置过期时间（结束时间）
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long endTime = seckillVoucher.getEndTime().toEpochSecond(ZoneOffset.UTC);
        stringRedisTemplate.expire(seckillVoucherKey, endTime-now, TimeUnit.SECONDS);
        stringRedisTemplate.expire(seckillVoucherUserSetKey, endTime-now, TimeUnit.SECONDS);

        // 创建消费者组（用作创建订单的消息队列）
        String key = RedisConstants.VOUCHER_ORDER_MESSAGE_LIST_KEY;
        stringRedisTemplate.opsForStream().createGroup(key, ReadOffset.from("0"), "g1");
    }

    /**
     * 将SeckillVoucher映射为Map
     * @param seckillVoucher
     * @return
     */
    private Map<String, String> getSeckillVoucherMap(SeckillVoucher seckillVoucher) {
        Map<String, String> seckillVoucherMap = new HashMap<>();
        seckillVoucherMap.put("id", String.valueOf(seckillVoucher.getVoucherId()));
        seckillVoucherMap.put("stock", String.valueOf(seckillVoucher.getStock()));
        seckillVoucherMap.put("beginTime", String.valueOf(seckillVoucher.getBeginTime()));
        seckillVoucherMap.put("endTime", String.valueOf(seckillVoucher.getEndTime()));

        return seckillVoucherMap;
    }
}
