package com.weyland.synthetic_human_core_starter.audit.publisher;

import com.weyland.synthetic_human_core_starter.audit.AuditMode;
import org.springframework.kafka.core.KafkaTemplate;

public class CompositeAuditPublisher implements AuditPublisher {
    private final ConsoleAuditPublisher consolePublisher;
    private final KafkaAuditPublisher kafkaPublisher;
    private AuditMode currentMode = AuditMode.CONSOLE;

    public CompositeAuditPublisher(KafkaTemplate<String, String> kafkaTemplate,
                                   String kafkaTopic) {
        this.consolePublisher = new ConsoleAuditPublisher();
        this.kafkaPublisher = kafkaTemplate != null ?
                new KafkaAuditPublisher(kafkaTemplate, kafkaTopic) : null;
    }

    public void setMode(AuditMode mode) {
        this.currentMode = mode;
    }

    @Override
    public void publish(String message) {
        switch (currentMode) {
            case CONSOLE:
                consolePublisher.publish(message);
                break;
            case KAFKA:
                if (kafkaPublisher != null) {
                    kafkaPublisher.publish(message);
                }
                break;
            case BOTH:
                consolePublisher.publish(message);
                if (kafkaPublisher != null) {
                    kafkaPublisher.publish(message);
                }
                break;
        }
    }
}