package edu.hw7.task1;

import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter extends Thread {

    private final AtomicInteger counter;
    private final int iterAmount;

    public ConcurrentCounter(AtomicInteger counter, int iterAmount) {
        this.counter = counter;
        this.iterAmount = iterAmount;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterAmount; i++) {
            increment();
        }
    }

    public void increment() {
        counter.addAndGet(1);
    }

}
