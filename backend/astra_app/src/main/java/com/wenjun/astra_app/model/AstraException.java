package com.wenjun.astra_app.model;

import com.wenjun.astra_app.model.enums.AstraExceptionEnum;

import lombok.Data;

@Data
public class AstraException extends Exception {
    private final Integer httpStatusCode;

    public AstraException(AstraExceptionEnum exception, String... args) {
        super(String.format(exception.getErrorMessage(), args));
        httpStatusCode = exception.getHttpStatusCode();
    }
}
