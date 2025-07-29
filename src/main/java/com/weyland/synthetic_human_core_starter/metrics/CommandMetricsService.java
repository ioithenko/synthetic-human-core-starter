package com.weyland.synthetic_human_core_starter.metrics;

import com.weyland.synthetic_human_core_starter.command.SyntheticCommand;
import io.micrometer.core.instrument.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CommandMetricsService {
    private final MeterRegistry meterRegistry;
    private final AtomicInteger queueSize;
    private final Map<String, Counter> authorCounters;

    public CommandMetricsService(MeterRegistry meterRegistry) {
        if (meterRegistry == null) {
            throw new IllegalArgumentException("MeterRegistry must not be null");
        }

        this.meterRegistry = meterRegistry;
        this.queueSize = new AtomicInteger(0);
        this.authorCounters = new ConcurrentHashMap<>();

        initMetrics();
    }

    private void initMetrics() {
        Gauge.builder("bishop.command.queue.size", queueSize, AtomicInteger::get)
                .description("Current number of commands in queue")
                .register(meterRegistry);
    }

    public void updateQueueSize(int currentSize) {
        this.queueSize.set(currentSize);
    }

    public void recordCommandAdded(SyntheticCommand command) {
        if (command == null) return;

        Counter.builder("bishop.commands.added")
                .tag("priority", command.getPriority().name())
                .register(meterRegistry)
                .increment();

        String author = command.getAuthor() != null ?
                command.getAuthor().trim().toLowerCase() :
                "unknown";

        authorCounters.computeIfAbsent(author,
                        a -> Counter.builder("bishop.commands.by.author")
                                .tag("author", a)
                                .register(meterRegistry))
                .increment();
    }

    public void recordCommandExecuted(SyntheticCommand command) {
        Counter.builder("bishop.commands.executed")
                .tag("priority", command.getPriority().name())
                .register(meterRegistry)
                .increment();
    }

    public void recordQueueOverflow() {
        Counter.builder("bishop.commands.errors")
                .tag("type", "queue_overflow")
                .register(meterRegistry)
                .increment();
    }
}