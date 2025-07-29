package com.weyland.synthetic_human_core_starter.command;

import com.weyland.synthetic_human_core_starter.audit.WeylandWatchingYou;
import com.weyland.synthetic_human_core_starter.audit.AuditMode;
import com.weyland.synthetic_human_core_starter.audit.publisher.AuditPublisher;
import com.weyland.synthetic_human_core_starter.command.queue.CommandQueue;
import com.weyland.synthetic_human_core_starter.exception.CommandQueueOverflowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessingService {
    private final CommandQueue queue;
    private final AuditPublisher auditPublisher;

    @Autowired
    public CommandProcessingService(CommandQueue queue, AuditPublisher auditPublisher) {
        this.queue = queue;
        this.auditPublisher = auditPublisher;
    }

    @WeylandWatchingYou(mode = AuditMode.BOTH)
    public void processCommand(SyntheticCommand command) {
        try {
            String auditMessage;
            if (command.getPriority() == CommandPriority.CRITICAL) {
                auditMessage = "CRITICAL: " + command.getDescription();
                executeCritical(command);
            } else {
                auditMessage = "QUEUED: " + command.getDescription();
                queue.add(command);
            }
            auditPublisher.publish(auditMessage);
        } catch (CommandQueueOverflowException e) {
            auditPublisher.publish("QUEUE_OVERFLOW: " + e.getMessage());
            throw e;
        }
    }

    @WeylandWatchingYou(mode = AuditMode.KAFKA)
    private void executeCritical(SyntheticCommand command) {
        System.out.println("Executing CRITICAL command: " + command.getDescription());
    }
}