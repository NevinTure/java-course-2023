package edu.hw7.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterRunner {

    public final AtomicInteger counter = new AtomicInteger();
    private final int iterAmount;
    private final int threadsNum;

    public CounterRunner(int iterAmount, int threadsNum) {
        this.iterAmount = iterAmount;
        this.threadsNum = threadsNum;
    }

    public void run() {
        CountDownLatch latch = new CountDownLatch(threadsNum);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadsNum; i++) {
            threads.add(new ConcurrentCounter(counter, iterAmount, latch));
            threads.get(i).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCounterValue() {
        return counter.get();
    }
}
