package com.wenjun.astra_app.model.enums;

import lombok.Getter;

@Getter
public enum AstraExceptionEnum {
    INVALID_PARAMETERS(400, "%s"),
    CONFLICT(409, "Existing %s found"),
    UNAUTHORIZED(401, "User is not logged in"),
    RESOURCE_NOT_FOUND_EXCEPTION(404, "%s cannot be found");

    private final Integer httpStatusCode;
    private final String errorMessage;

    AstraExceptionEnum(Integer httpStatusCode, String errorMessage) {
        this.httpStatusCode = httpStatusCode;
        this.errorMessage = errorMessage;
    };
}
