package edu.hw8.task2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskRunner extends Thread {

    private final int n;
    private final Worker[] pool;
    private final AtomicBoolean[] working;
    private final LinkedBlockingQueue<Runnable> queue;
    private final AtomicBoolean isInterrupted;

    public TaskRunner(int n, LinkedBlockingQueue<Runnable> queue) {
        this.n = n;
        this.queue = queue;
        this.pool = new Worker[n];
        this.working = new AtomicBoolean[n];
        this.isInterrupted = new AtomicBoolean(false);
    }

    public void init() {
        for (int i = 0; i < n; i++) {
            working[i] = new AtomicBoolean(false);
            pool[i] = new Worker(working[i], isInterrupted);
            pool[i].start();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted.get()) {
            if (!queue.isEmpty()) {
                for (int i = 0; i < n; i++) {
                    if (isInterrupted.get()) {
                        break;
                    }
                    if (!working[i].get()) {
                        working[i].set(true);
                        pool[i].execute(queue.poll());
                        break;
                    }
                }
            }
        }
    }

    public void add(Runnable runnable) {
        queue.add(runnable);
    }

    public void interruptAll() {
        while (!queue.isEmpty()) {
            Thread.onSpinWait();
        }
        isInterrupted.set(true);
        try {
            for (int i = 0; i < n; i++) {
                if (working[i].get()) {
                    pool[i].join();
                } else {
                    pool[i].interrupt();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
