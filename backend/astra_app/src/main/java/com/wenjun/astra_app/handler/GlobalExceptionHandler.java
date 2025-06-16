package com.wenjun.astra_app.handler;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.ResponseWrapper;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import static io.opentelemetry.instrumentation.api.incubator.log.LoggingContextConstants.TRACE_ID;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AstraException.class)
    public ResponseWrapper handle(HttpServletRequest request, AstraException exception) {
        return new ResponseWrapper(
                null, false, exception.getMessage(), exception.getHttpStatusCode(), MDC.get(TRACE_ID));
    }

    @ExceptionHandler(Exception.class)
    public ResponseWrapper handle(HttpServletRequest req, Exception e) {
        return new ResponseWrapper(null, false, e.getMessage(), 500, MDC.get(TRACE_ID));
    }
}
