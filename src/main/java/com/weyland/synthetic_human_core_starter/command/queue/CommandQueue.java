package com.weyland.synthetic_human_core_starter.command.queue;

import com.weyland.synthetic_human_core_starter.exception.CommandQueueOverflowException;
import com.weyland.synthetic_human_core_starter.command.SyntheticCommand;

public interface CommandQueue {
    void add(SyntheticCommand command) throws CommandQueueOverflowException;
    SyntheticCommand poll();

    int size();
}