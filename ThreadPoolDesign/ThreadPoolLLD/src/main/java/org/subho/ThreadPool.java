package org.subho;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool {
    private static ThreadPool INSTANCE;
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workers;
    private final AtomicBoolean isShutdownInitiated;

    private ThreadPool(int numOfThreads) {
        taskQueue = new LinkedBlockingQueue<>();
        workers = new Thread[numOfThreads];
        isShutdownInitiated = new AtomicBoolean(false);

        for(int i=0; i<numOfThreads; i++) {
            workers[i] = new Worker(taskQueue, isShutdownInitiated);
            workers[i].start();
        }
    }

    public static synchronized ThreadPool getInstance(int numOfThreads) {
        if(INSTANCE==null) {
            INSTANCE = new ThreadPool(numOfThreads);
        }
        return INSTANCE;
    }

    public void submitTask(Runnable task) {
        if(!isShutdownInitiated.get()) {
            taskQueue.offer(task);
        }
    }

    public void shutdown() {
        System.out.println("Shutdown initiated!!");
        isShutdownInitiated.set(true);
        for(int i=0; i<workers.length; i++) {
            workers[i].interrupt();
        }
    }
}
