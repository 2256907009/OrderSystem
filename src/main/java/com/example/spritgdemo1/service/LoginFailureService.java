package com.example.spritgdemo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class LoginFailureService {



    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    // 限制登录失败次数的时间间隔（单位：毫秒）
    private static final long LOGIN_LIMIT_DURATION = 5 * 60 * 1000; // 5分钟

    public void recordLoginFailures(String username) {
        String key = "loginFailure:" + username;

        Integer failures = (Integer) redisTemplate.opsForValue().get(key);
        if(failures == null) {
            failures = 1;
        }else{
            failures ++;
        }
        redisTemplate.opsForValue().set(key, failures,LOGIN_LIMIT_DURATION, TimeUnit.SECONDS);


    }



    public int getLoginFailures(String username) {
        String key = "loginFailure:" + username;
        Integer failuresCount = (Integer) redisTemplate.opsForValue().get(key);
        return failuresCount != null ? failuresCount : 0;

    }



}
