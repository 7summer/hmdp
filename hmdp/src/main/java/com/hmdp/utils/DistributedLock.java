package com.hmdp.utils;


import com.hmdp.constant.RedisConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class DistributedLock {

    private StringRedisTemplate stringRedisTemplate;

    private static final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    private static final RedisScript<Long> redisScript;
    static {
        // 得到lua脚本
        redisScript = RedisScript.of(new ClassPathResource("/lua/unlock.lua"), Long.class);
    }

    public DistributedLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public boolean tryLock(String key) {
        // 尝试获取分布式锁，并设置过期时间
        Boolean result = stringRedisTemplate.opsForValue()
                .setIfAbsent(key, uuid + ":" + Thread.currentThread().getId(),
                        RedisConstants.DISTRIBUTED_LOCK_TTL, TimeUnit.MINUTES);

        return result != null ? result : false;
    }

    public void unlock(String key) {
        Long execute = stringRedisTemplate.execute(redisScript, Collections.singletonList(key),
                uuid + ":" + Thread.currentThread().getId());
    }
}
