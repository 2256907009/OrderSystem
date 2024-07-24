package com.example.spritgdemo1.utils;

import com.wlkjyy.Capsule.Manager;
import com.wlkjyy.Eloquent.DB;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: wlkjyy <wlkjyy@vip.qq.com>
 * @Date: 2024/3/27
 * @Project: jianzhan
 */
@Service
public class dbs {

    @Bean
    public DB DB(){
        Manager manager = new Manager();
        manager.addConnection(new HashMap<>(){
            {
                put("host", "localhost");
                put("port", 3306);
                put("username", "root");
                put("password", "wq123456");
                put("database", "javaweb");
            }
        });

        return manager.getEloquentInstance();
    }

}