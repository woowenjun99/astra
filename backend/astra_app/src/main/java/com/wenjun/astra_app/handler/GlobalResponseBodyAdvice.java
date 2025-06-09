package com.wenjun.astra_app.handler;

import com.wenjun.astra_app.model.ResponseWrapper;
import com.wenjun.astra_app.util.ThreadLocalUser;

import lombok.AllArgsConstructor;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@AllArgsConstructor
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        // We need to clear the current thread for future uses
        if (ThreadLocalUser.get() != null) {
            ThreadLocalUser.clear();
        }

        // Double wrapping might occur when we throw an exception
        // We need to handle the status code as well
        if (body instanceof ResponseWrapper<?>) {
            response.setStatusCode(HttpStatusCode.valueOf(((ResponseWrapper) body).getCode()));
            return body;
        }

        return new ResponseWrapper<>(body, true, "", 200);
    }
}
