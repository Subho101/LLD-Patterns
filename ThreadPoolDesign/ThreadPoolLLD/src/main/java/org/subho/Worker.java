package org.subho;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread{

    //private final BlockingQueue<Runnable> taskQueue;
    private final TaskScheduler taskQueue;
    private final AtomicBoolean isShutdownInitiated;

    /*public Worker(BlockingQueue<Runnable> taskQueue, AtomicBoolean isShutdownInitiated) {
        this.taskQueue = taskQueue;
        this.isShutdownInitiated = isShutdownInitiated;
    }*/

    public Worker(TaskScheduler taskQueue, AtomicBoolean isShutdownInitiated) {
        this.taskQueue = taskQueue;
        this.isShutdownInitiated = isShutdownInitiated;
    }

    @Override
    public void run() {
        System.out.println("Started worker " + Thread.currentThread().getName());

        while(true) {
            System.out.println("[status-"+Thread.currentThread().getName()+"]" + " shutdown:" + isShutdownInitiated.get() + " | task:"+taskQueue.size());
            //System.out.println("Shutdown status " + isShutdownInitiated.get());
            try{
                if(isShutdownInitiated.get() && taskQueue.isEmpty()) {
                    break;
                }
                //Runnable task = taskQueue.poll();
                Runnable task = taskQueue.fetchTask();
                if(task != null) {
                    task.run();
                } else {
                    Thread.sleep(100);
                }
            } catch (InterruptedException ie) {
                System.out.println("Worker " + Thread.currentThread().getName() + " interrupted " + ie);
                break;
            }
        }
        System.out.println("[status-"+Thread.currentThread().getName()+"]" + " shutdown:" + isShutdownInitiated.get() + " | task:"+taskQueue.size());
        System.out.println("Stopped worker " + Thread.currentThread().getName());
    }
}
