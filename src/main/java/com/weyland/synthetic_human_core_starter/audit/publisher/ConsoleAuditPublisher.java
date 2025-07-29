package com.weyland.synthetic_human_core_starter.audit.publisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleAuditPublisher implements AuditPublisher {
    @Override
    public void publish(String message) {
        log.info("[CONSOLE-AUDIT]: " + message);
    }

}
