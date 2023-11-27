package edu.hw7.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class PiApproximatorMultiThread {

    @SuppressWarnings("MagicNumber")
    public double approximate(int n, int threadsAmount) {
        int iterByThread = n / threadsAmount;
        CountDownLatch latch = new CountDownLatch(threadsAmount);
        List<ApproximatePiThread> threads = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            threads.add(new ApproximatePiThread(iterByThread, latch));
            threads.get(i).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int totalCount = threads
            .stream()
            .mapToInt(ApproximatePiThread::getTotalCount)
            .sum();
        int circleCount = threads
            .stream()
            .mapToInt(ApproximatePiThread::getCircleCount)
            .sum();
        return (double) (4 * circleCount) / totalCount;
    }
}
