package edu.hw7.task1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCounter extends Thread {

    private final AtomicInteger counter;
    private final int iterAmount;
    private final CountDownLatch latch;

    public ConcurrentCounter(AtomicInteger counter, int iterAmount, CountDownLatch latch) {
        this.counter = counter;
        this.iterAmount = iterAmount;
        this.latch = latch;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterAmount; i++) {
            increment();
        }
        latch.countDown();
    }

    public void increment() {
        counter.addAndGet(1);
    }

}
