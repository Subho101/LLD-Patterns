package org.subho;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityScheduler implements TaskScheduler{
    private final PriorityBlockingQueue<Runnable> taskQueue =
            new PriorityBlockingQueue<>(10, Comparator.comparingInt(Objects::hashCode));

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
