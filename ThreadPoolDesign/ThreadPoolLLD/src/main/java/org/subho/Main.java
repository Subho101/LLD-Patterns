package org.subho;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ThreadPool threadPool = ThreadPool.getInstance(3);
        for(int i=0; i<10; i++) {
            threadPool.submitTask(new Task("Task-" + (i+1)));
        }

        try {
            Thread.sleep(1000);
            threadPool.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}