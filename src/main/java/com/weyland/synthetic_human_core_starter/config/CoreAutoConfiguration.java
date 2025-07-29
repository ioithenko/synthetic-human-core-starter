package com.weyland.synthetic_human_core_starter.config;

import com.weyland.synthetic_human_core_starter.audit.publisher.*;
import com.weyland.synthetic_human_core_starter.command.CommandProcessingService;
import com.weyland.synthetic_human_core_starter.command.queue.CommandQueue;
import com.weyland.synthetic_human_core_starter.command.queue.DefaultCommandQueue;
import com.weyland.synthetic_human_core_starter.metrics.CommandMetricsService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Optional;

@Configuration
public class CoreAutoConfiguration {

    @Value("${weyland.bishop.core.command.queue-capacity:100}")
    private int queueCapacity;

    @Bean
    public CommandQueue commandQueue() {
        return new DefaultCommandQueue(queueCapacity);
    }

    @Bean
    public AuditPublisher auditPublisher(
            Optional<KafkaTemplate<String, String>> kafkaTemplate,
            @Value("${weyland.bishop.core.audit.kafka.topic:audit-logs}") String kafkaTopic) {

        return new CompositeAuditPublisher(
                kafkaTemplate.orElse(null),
                kafkaTopic
        );
    }

    @Bean
    @ConditionalOnClass(KafkaTemplate.class)
    @ConditionalOnProperty(
            name = "weyland.bishop.core.audit.mode",
            havingValue = "KAFKA,BOTH",
            matchIfMissing = false
    )
    public KafkaTemplate<String, String> kafkaTemplate(
            ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public CommandProcessingService commandProcessingService(
            CommandQueue queue,
            AuditPublisher publisher) {
        return new CommandProcessingService(queue, publisher);
    }

    @Bean
    public CommandMetricsService commandMetricsService(MeterRegistry meterRegistry) {
        return new CommandMetricsService(meterRegistry);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "synthetic-human-core");
    }

}