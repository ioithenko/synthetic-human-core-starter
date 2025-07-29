package com.weyland.synthetic_human_core_starter.exception;


import lombok.Getter;

@Getter
public class CoreException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public CoreException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

}
