package com.example.intern_bot.app;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Cache {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY = "seen_jobs";

    public Cache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isNew(String url) {
        Long added = redisTemplate.opsForSet().add(KEY, url);
        return added != null && added > 0;
    }
}