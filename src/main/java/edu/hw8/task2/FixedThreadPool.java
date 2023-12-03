package edu.hw8.task2;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private final TaskRunner taskRunner;
    private final boolean isInterrupted;

    private FixedThreadPool(int n) {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        isInterrupted = false;
        this.taskRunner = new TaskRunner(n, queue);
    }

    public static FixedThreadPool create(int n) {
        return new FixedThreadPool(n);
    }

    @Override
    public void start() {
        taskRunner.init();
        taskRunner.start();
    }

    @Override
    public void execute(Runnable runnable) {
        if (!isInterrupted) {
            taskRunner.add(runnable);
        }
    }

    @Override
    public void close() {
        taskRunner.interruptAll();
    }
}
