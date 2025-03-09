package org.subho;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FIFOScheduler implements TaskScheduler{
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    @Override
    public void submitTask(Runnable task) {
        taskQueue.offer(task);
    }

    @Override
    public Runnable fetchTask() throws InterruptedException {
        return taskQueue.poll();
    }

    @Override
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    @Override
    public int size() {
        return taskQueue.size();
    }
}
