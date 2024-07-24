package com.example.spritgdemo1;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceTest {
    public static void main(String[] args) {


        RedisURI redisURI = RedisURI.builder().redis("localhost", 6379)
                .withPassword("621700")
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.set("user:100","{\"name\":\"lin\",\"age\":\"18\"}");

        String value = syncCommands.get("user:100");
        System.out.println("value:" + value);
        connection.close();
        redisClient.shutdown();




    }









}
