package com.weyland.synthetic_human_core_starter.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String errorCode, String message) {
        this.message = message;
        String timestamp = Instant.now().toString();
    }

}
