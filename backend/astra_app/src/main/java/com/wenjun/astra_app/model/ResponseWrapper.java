package com.wenjun.astra_app.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class ResponseWrapper<T> {
    private final T data;
    private final boolean isSuccess;
    private final String message;
    private final Date date = new Date();
    private final Long version = 1L;
    private final Integer code;
    private final String traceId;

    public ResponseWrapper(T data, boolean isSuccess, String message, Integer code, String traceId) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
        this.traceId = traceId;
    }
}
