package com.weyland.synthetic_human_core_starter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommandQueueOverflowException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ErrorResponse handleQueueOverflow(CommandQueueOverflowException ex) {
        return new ErrorResponse("QUEUE_OVERFLOW", ex.getMessage());
    }

    private record ErrorResponse(String code, String message) {}
}