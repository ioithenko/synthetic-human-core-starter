package com.weyland.synthetic_human_core_starter.audit;

import com.weyland.synthetic_human_core_starter.audit.publisher.AuditPublisher;
import com.weyland.synthetic_human_core_starter.audit.publisher.CompositeAuditPublisher;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditAspect {
    private final AuditPublisher auditPublisher;

    @Autowired
    public AuditAspect(AuditPublisher auditPublisher) {
        this.auditPublisher = auditPublisher;
    }

    @Around("@annotation(weylandWatchingYou)")
    public Object auditMethod(ProceedingJoinPoint joinPoint,
                              WeylandWatchingYou weylandWatchingYou) throws Throwable {

        if (auditPublisher instanceof CompositeAuditPublisher) {
            ((CompositeAuditPublisher) auditPublisher).setMode(weylandWatchingYou.mode());
        }

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Object result = null;
        Throwable exception = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            exception = t;
            throw t;
        } finally {
            String message = buildAuditMessage(methodName, args, result, exception);
            auditPublisher.publish(message);
        }
    }

    private String buildAuditMessage(String methodName, Object[] args, Object result, Throwable exception) {
        StringBuilder sb = new StringBuilder();
        sb.append("Method: ").append(methodName).append(" | ");
        sb.append("Args: ").append(args != null ? arrayToString(args) : "null").append(" | ");
        sb.append("Result: ").append(result != null ? result.toString() : "void").append(" | ");
        sb.append("Status: ").append(exception != null ? "FAILED" : "SUCCESS").append(" | ");
        if (exception != null) {
            sb.append("Error: ").append(exception.getMessage());
        }
        return sb.toString();
    }

    private String arrayToString(Object[] array) {
        if (array == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i] != null ? array[i].toString() : "null");
            if (i < array.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}