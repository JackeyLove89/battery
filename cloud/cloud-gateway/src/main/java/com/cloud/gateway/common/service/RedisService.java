package com.cloud.gateway.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 * 
 * @author cloud
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public final static String SECURITY_CACHE_PREFIX = "springboot:";

    public void set(String key, String value, long expire) {
        stringRedisTemplate.opsForValue().set(SECURITY_CACHE_PREFIX+key, value, expire, TimeUnit.SECONDS);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(SECURITY_CACHE_PREFIX+key,value);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(SECURITY_CACHE_PREFIX+key);
    }

    public void remove(String key) {
        stringRedisTemplate.delete(SECURITY_CACHE_PREFIX+key);
    }
}
