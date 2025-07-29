package com.weyland.synthetic_human_core_starter.exception;

public class CommandQueueOverflowException extends RuntimeException {
    public CommandQueueOverflowException(String message) {
        super(message);
    }

    public CommandQueueOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
