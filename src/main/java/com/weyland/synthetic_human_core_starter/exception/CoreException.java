package com.weyland.synthetic_human_core_starter.exception;


public class CoreException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public CoreException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }

    public CoreException(String errorCode, String message) {
        this(new ErrorResponse(errorCode, message));
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
