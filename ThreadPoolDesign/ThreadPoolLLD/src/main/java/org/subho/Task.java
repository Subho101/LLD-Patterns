package org.subho;

public class Task implements Runnable {
    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("Executing task " + taskName + " with " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e + " " + Thread.currentThread().getName());
        }
        System.out.println("Execution completed " + taskName + " for " + Thread.currentThread().getName());
    }
}
