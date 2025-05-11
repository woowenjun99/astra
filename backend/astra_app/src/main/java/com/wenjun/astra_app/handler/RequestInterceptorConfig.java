package com.wenjun.astra_app.handler;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
public class RequestInterceptorConfig implements WebMvcConfigurer {
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(requestInterceptor)
                .excludePathPatterns("/users/register");
    }
}
