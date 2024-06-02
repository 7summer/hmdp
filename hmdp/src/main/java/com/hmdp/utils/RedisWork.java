package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class RedisWork {
    private StringRedisTemplate stringRedisTemplate;

    private static final long BASIC_TIME = 1640995200L;

    public RedisWork(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        // 生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowTime = now.toEpochSecond(ZoneOffset.UTC);

        long timestamp = nowTime - BASIC_TIME;

        // 生成序列号（由Redis生成）
        // 加上日期方便统计每天的订单数，也避免日积月累的订单数超过Long类型最大值
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        Long serializationId = stringRedisTemplate.opsForValue().increment(keyPrefix + date);

        return  (timestamp << 32) | serializationId;
    }
}
