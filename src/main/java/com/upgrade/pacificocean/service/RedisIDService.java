package com.upgrade.pacificocean.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

@Component
public class RedisIDService {

	RedisAtomicInteger atomicLong;

    @Autowired
    public RedisIDService(RedisTemplate<String, Integer> idTemplate){
        this.atomicLong = new RedisAtomicInteger("counter", idTemplate);
    }

    public int getNextID(){
        return atomicLong.incrementAndGet();
    }
}