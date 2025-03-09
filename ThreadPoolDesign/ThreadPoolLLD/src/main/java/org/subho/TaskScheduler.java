package org.subho;

public interface TaskScheduler {
    void submitTask(Runnable task);
    Runnable fetchTask() throws InterruptedException;
    boolean isEmpty();
    int size();
}
