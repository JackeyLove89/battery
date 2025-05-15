package com.cloud.base.config;

import com.cloud.base.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new Exception("Token缺失");
        }
        token = token.substring(7);

        if (!JwtUtil.validateToken(token)) {
            throw new Exception("Token无效或已过期");
        }
        return true;
    }
}