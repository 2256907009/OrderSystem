package com.example.spritgdemo1.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    public static String generateRandomToken(int length) {
        byte[] randomBytes = new byte[length];
        new SecureRandom().nextBytes(randomBytes);//生成随机字节根据长度
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
