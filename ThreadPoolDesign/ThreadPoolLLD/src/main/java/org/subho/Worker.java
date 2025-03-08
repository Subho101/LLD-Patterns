package org.subho;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread{

    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean isShutdownInitiated;

    public Worker(BlockingQueue<Runnable> taskQueue, AtomicBoolean isShutdownInitiated) {
        this.taskQueue = taskQueue;
        this.isShutdownInitiated = isShutdownInitiated;
    }

    @Override
    public void run() {
        System.out.println("Started worker " + Thread.currentThread().getName());

        while(!isShutdownInitiated.get() || !taskQueue.isEmpty()) {
            System.out.println("[status-"+Thread.currentThread().getName()+"]" + " shutdown:" + isShutdownInitiated.get() + " | task:"+taskQueue.size());
            //System.out.println("Shutdown status " + isShutdownInitiated.get());
            try{
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException ie) {
                System.out.println("Exception " + ie);
                if(isShutdownInitiated.get()) {

                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("[status-"+Thread.currentThread().getName()+"]" + " shutdown:" + isShutdownInitiated.get() + " | task:"+taskQueue.size());
        System.out.println("Stopped worker " + Thread.currentThread().getName());
    }
}
