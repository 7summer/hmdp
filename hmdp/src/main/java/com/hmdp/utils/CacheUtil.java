package com.hmdp.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hmdp.constant.RedisConstants;
import com.hmdp.common.RedisData;
import com.hmdp.common.ReturnObjectStatus;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class CacheUtil {
    private StringRedisTemplate stringRedisTemplate;

    private static final ExecutorService cacheRebuiltExecutor = Executors.newFixedThreadPool(10);

    public CacheUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 存储数据（设置逻辑过期时间）
     * @param key key键
     * @param value 数据
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit timeUnit) {
        // 设置逻辑过期
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(timeUnit.toSeconds(time)));
        // 写入redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    /**
     * 获取ReturnObjectStatus
     * @param key key键
     * @param clazz 数据类型
     * @return 根据key键获取的值确定是否需要缓存重建
     */
    public <R> ReturnObjectStatus getWithLogicalExpire(String key, Class<R> clazz) {
        ReturnObjectStatus returnObjectStatus = new ReturnObjectStatus();

        // 查看redis数据库是否存在数据
        String redisDataJson = stringRedisTemplate.opsForValue().get(key);
        if (redisDataJson == null || redisDataJson.length() == 0) {
            returnObjectStatus.setData(null);
            returnObjectStatus.setStatus(true);

            return returnObjectStatus;
        }

        // 检查数据的过期时间
        RedisData redisData = JSONUtil.toBean(redisDataJson, RedisData.class);
        LocalDateTime localDateTime = redisData.getExpireTime();
        JSONObject data = (JSONObject) redisData.getData();
        R r = JSONUtil.toBean(data, clazz);

        // 时间未过期，直接返回
        // 时间过期，不直接返回
        returnObjectStatus.setData(r);
        returnObjectStatus.setStatus(localDateTime.isAfter(LocalDateTime.now()));

        return returnObjectStatus;
    }

    /**
     * 获取数据（解决缓存穿透）
     * @param keyPrefix key前缀
     * @param id 执行数据回调的参数
     * @param clazz 数据类型
     * @param dataCallback 数据回调
     * @param time 时间
     * @param timeUnit 时间类型
     * @return
     * @param <R> 数据类型
     * @param <ID> id类型
     */
    public <R, ID> R queryWithPassThrough(
            String keyPrefix, ID id,
            Class<R> clazz, Function<ID, R> dataCallback,
            Long time, TimeUnit timeUnit) {

        String key = keyPrefix + id;

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        // 查看redis数据库是否存在数据
        String json = operations.get(key);
        if (json != null && json.length() > 0) {
            // 如果有缓存，更新过期时间
            stringRedisTemplate.expire(key, time, timeUnit);
            return JSONUtil.toBean(json, clazz);
        }
        if (json != null) {
            stringRedisTemplate.expire(key, RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }

        // redis数据库没缓存，从mysql数据库中查询数据
        R r = dataCallback.apply(id);
        if (r == null) {
            // 防止缓存穿透
            operations.set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }

        // 将shop数据缓存到redis
        // 设置过期时间
        String rJson = JSONUtil.toJsonStr(r);
        operations.set(key, rJson, time, timeUnit);

        return r;
    }

    /**
     * 获取数据（解决缓存穿透、缓存击穿）
     * @param keyPrefix key前缀
     * @param id 执行数据回调的参数
     * @param clazz 数据类型
     * @param dataCallback 数据回调
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     * @return
     * @param <R> 数据类型
     * @param <ID> id类型
     */
    public <R, ID> R queryWithLogicalExpire(
            String keyPrefix, ID id,
            Class<R> clazz, Function<ID, R> dataCallback,
            Long time, TimeUnit timeUnit) {
        String key = RedisConstants.CACHE_SHOP_KEY + id;

        // 查看redis数据库是否存在数据
        // 检查数据的过期时间
        // 时间未过期，直接返回
        ReturnObjectStatus returnObjectStatus = getWithLogicalExpire(key, clazz);
        R r = (R) returnObjectStatus.getData();
        if (r == null) {
            return null;
        }
        if (returnObjectStatus.getStatus()) {
            return r;
        }

        // 获取互斥锁成功，开启新线程异步重建缓存，原线程直接返回旧数据
        // 再次执行前面的操作（DoubleCheck）
        if (tryLock(RedisConstants.LOCK_SHOP_KEY + id)) {
            ReturnObjectStatus againReturnObjectStatus = getWithLogicalExpire(key, clazz);
            R againR = (R) againReturnObjectStatus.getData();
            if (againR == null) {
                return null;
            }
            if (againReturnObjectStatus.getStatus()) {
                return againR;
            }

            cacheRebuiltExecutor.submit(() -> {
                try {
                    rebuilt(keyPrefix, id, dataCallback, time, timeUnit);
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    unLock(RedisConstants.LOCK_SHOP_KEY + id);
                }
            });

            return againR;
        }

        // 获取互斥锁失败，直接返回旧数据
        return r;
    }

    /**
     * 获取锁
     * @param key
     * @return
     */
    private boolean tryLock(String key) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", RedisConstants.LOCK_SHOP_TTL, TimeUnit.MINUTES);
        return result != null && result;
    }

    /**
     * 释放锁
     * @param key
     */
    private void unLock(String key) {
        stringRedisTemplate.delete(key);
    }


    /**
     * 重建缓存数据
     * @param keyPrefix
     * @param id
     * @param dataCallback
     * @param time
     * @param timeUnit
     * @param <ID>
     * @param <R>
     * @throws InterruptedException
     */
    private <ID, R> void rebuilt(
            String keyPrefix, ID id,
            Function<ID, R> dataCallback,
            Long time, TimeUnit timeUnit) throws InterruptedException {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

        Thread.sleep(2000);

        String key = keyPrefix + id;

        R r = dataCallback.apply(id);
        if (r == null) {
            // 防止缓存穿透
            operations.set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return;
        }

        // 写入redis
        setWithLogicalExpire(key, r, time, timeUnit);
    }
}
