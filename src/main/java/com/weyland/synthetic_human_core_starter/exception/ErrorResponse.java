package com.weyland.synthetic_human_core_starter.exception;

import lombok.Getter;

import java.time.Instant;

public class ErrorResponse {
    private final String errorCode;
    @Getter
    private final String message;
    private final String timestamp;
    private String details;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = Instant.now().toString();
    }

}
