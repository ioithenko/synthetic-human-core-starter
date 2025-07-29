package com.weyland.synthetic_human_core_starter.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public Object[] getArgs() {
        return args != null ? Arrays.copyOf(args, args.length) : null;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> auditData = new HashMap<>();
            auditData.put("timestamp", timestamp);
            auditData.put("androidId", androidId);
            auditData.put("method", methodName);
            auditData.put("args", args != null ? Arrays.toString(args) : "null");
            auditData.put("result", result != null ? result.toString() : "void");
            auditData.put("status", exception != null ? "FAILED" : "SUCCESS");
            auditData.put("error", exception != null ? exception.getMessage() : null);

            return mapper.writeValueAsString(auditData);
        } catch (JsonProcessingException e) {
            return String.format(
                    "{\"error\":\"JSON serialization failed\", \"method\":\"%s\"}",
                    methodName
            );
        }
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