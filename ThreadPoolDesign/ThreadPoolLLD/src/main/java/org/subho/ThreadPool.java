package org.subho;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool {
    private static ThreadPool INSTANCE;
    //private final BlockingQueue<Runnable> taskQueue;
    private final TaskScheduler taskQueue;
    private final Thread[] workers;
    private final AtomicBoolean isShutdownInitiated;

    private ThreadPool(int numOfThreads, TaskScheduler scheduler) {
        //taskQueue = new LinkedBlockingQueue<>();
        taskQueue = scheduler;
        workers = new Thread[numOfThreads];
        isShutdownInitiated = new AtomicBoolean(false);

        for(int i=0; i<numOfThreads; i++) {
            workers[i] = new Worker(taskQueue, isShutdownInitiated);
            workers[i].start();
        }
    }

    public static synchronized ThreadPool getInstance(int numOfThreads, TaskScheduler scheduler) {
        if(INSTANCE==null) {
            INSTANCE = new ThreadPool(numOfThreads, scheduler);
        }
        return INSTANCE;
    }

    public void submitTask(Runnable task) {
        if(!isShutdownInitiated.get()) {
            //taskQueue.offer(task);
            taskQueue.submitTask(task);
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
