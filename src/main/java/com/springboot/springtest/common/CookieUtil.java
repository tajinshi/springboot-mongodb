package com.springboot.springtest.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *
 */
public class CookieUtil {
    //登录失败次数cookie的key
    public static final String LOGIN_FAIL_COUNT_COOKIE = "LOGIN_FAIL_COUNT";


    /**
     * 添加cookie
     *
     * @param response http响应对象
     * @param key      cookie key
     * @param value    cookie value
     * @param expired  过期时间
     * @param path     路径
     * @param domain   域
     */
    public static void addCookie(HttpServletResponse response, String key, String value, int expired, String path, String domain) {
        response.setContentType("text/html;charset=utf-8");
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expired);
        cookie.setPath(path);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    /**
     * 从cookie中获取登录失败次数
     *
     * @param request
     * @return
     */
    public static int getFailCount(HttpServletRequest request) {
        int result = 0;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (LOGIN_FAIL_COUNT_COOKIE.equals(cookie.getName())) {
                    result = Integer.parseInt(cookie.getValue().toString());
                }
            }
        }
        return result;
    }
    /**
     * 获取制定cookie的值
     */
    public static String getCookieByName(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookieName,cookie.getName())) {
                   return  cookie.getValue().toString();
                }
            }
        }
        return "";
    }
}
