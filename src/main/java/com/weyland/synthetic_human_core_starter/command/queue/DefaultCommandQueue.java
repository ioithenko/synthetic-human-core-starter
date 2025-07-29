package com.weyland.synthetic_human_core_starter.command.queue;

import com.weyland.synthetic_human_core_starter.command.SyntheticCommand;
import com.weyland.synthetic_human_core_starter.exception.CommandQueueOverflowException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DefaultCommandQueue implements CommandQueue {
    private final BlockingQueue<SyntheticCommand> queue;

    public DefaultCommandQueue(int capacity) {
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public void add(SyntheticCommand command) throws CommandQueueOverflowException {
        if (!queue.offer(command)) {
            throw new CommandQueueOverflowException("Command queue capacity exceeded");
        }
    }

    @Override
    public SyntheticCommand poll() {
        return queue.poll();
    }

    @Override
    public int size() {
        return queue.size();
    }

}