package com.wenjun.astra_app.handler;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.ResponseWrapper;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AstraException.class)
    public ResponseWrapper handle(HttpServletRequest request, AstraException exception) {
        return new ResponseWrapper(null, false, exception.getMessage(), exception.getHttpStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseWrapper handle(HttpServletRequest req, Exception e) {
        return new ResponseWrapper(null, false, e.getMessage(), 500);
    }
}
