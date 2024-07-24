package com.example.spritgdemo1.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class CookieUtil {
    // 设置Cookie
    public static String setCookie(HttpServletResponse response, String randomToken, int maxAge) {
        Cookie cookie = new Cookie("cookie",randomToken);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge); // 设置Cookie的最大存活时间，单位为秒
        response.addCookie(cookie);
        return cookie.getValue();

    }


    // 删除Cookie
    public static void deleteCookie(HttpServletResponse response, String randomToken) {
        Cookie cookie = new Cookie("token", randomToken);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 设置最大存活时间为0，即立即删除
        response.addCookie(cookie);
    }

    // 判断Cookie是否过期
    public static boolean isCookieExpired(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        //测试
        System.out.println(Arrays.toString(cookies));

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    System.out.println(cookie.getMaxAge() <= 0);
                    return cookie.getMaxAge() <= 0;
                }
            }
        }
        return true; // Cookie不存在或已过期
    }

    // 重新生成Cookie
    public static void regenerateCookie(HttpServletResponse response, String randomToken, int maxAge) {
        deleteCookie(response, randomToken); // 删除原有的Cookie
        setCookie(response, randomToken, maxAge); // 创建新的Cookie
    }
}
