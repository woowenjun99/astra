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

    public ResponseWrapper(T data, boolean isSuccess, String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
