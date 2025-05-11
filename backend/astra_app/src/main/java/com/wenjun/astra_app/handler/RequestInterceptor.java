package com.wenjun.astra_app.handler;

import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.impl.ThreadLocalUser;

import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Component
public class RequestInterceptor implements HandlerInterceptor {
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        FirebaseToken token = authService.validate(jwt);
        ThreadLocalUser.set(token);
        return true;
    }
}
