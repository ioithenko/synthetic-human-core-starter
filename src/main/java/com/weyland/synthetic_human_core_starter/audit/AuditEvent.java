package com.weyland.synthetic_human_core_starter.audit;

import lombok.Getter;

import java.util.Arrays;

public class AuditEvent {
    @Getter
    private final AuditMode mode;
    @Getter
    private final String methodName;
    private final Object[] args;
    @Getter
    private final Object result;
    @Getter
    private final Throwable exception;
    @Getter
    private final long timestamp;
    @Getter
    private final String androidId;

    public AuditEvent(AuditMode mode, String methodName, Object[] args,
                      Object result, Throwable exception, String androidId) {
        this.mode = mode;
        this.methodName = methodName;
        this.args = args != null ? Arrays.copyOf(args, args.length) : null;
        this.result = result;
        this.exception = exception;
        this.timestamp = System.currentTimeMillis();
        this.androidId = androidId;
    }

    @Override
    public String toString() {
        return String.format(
                "[%s] %s.%s(args=%s) | result=%s | error=%s",
                androidId,
                methodName,
                Arrays.toString(args),
                result != null ? result.toString() : "void",
                exception != null ? exception.getMessage() : "none"
        );
    }
}