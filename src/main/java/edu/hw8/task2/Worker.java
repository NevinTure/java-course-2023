package edu.hw8.task2;

import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread {

    private volatile Runnable task;
    private final AtomicBoolean isWorking;
    private final AtomicBoolean isInterrupted;

    public Worker(AtomicBoolean isWorking, AtomicBoolean isInterrupted) {
        this.isWorking = isWorking;
        this.isInterrupted = isInterrupted;
    }

    @SuppressWarnings("RegexpSinglelineJava")
    @Override
    public void run() {
        while (!isInterrupted.get()) {
            while (task == null) {
                Thread.onSpinWait();
            }
            try {
                if (!this.isInterrupted() && task != null) {
                    task.run();
                }
                task = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isWorking.set(false);
            }
        }
    }

    public void execute(Runnable task) {
        this.task = task;
    }
}
