package edu.hw7.task4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class ApproximatePiThread extends Thread {

    private final static double RADIUS = 0.5;
    private final static double CENTER_X = 0.5;
    private final static double CENTER_Y = 0.5;
    private final ThreadLocalRandom random;
    private final int iterAmount;
    private final CountDownLatch latch;
    private int totalCount;
    private int circleCount;

    public ApproximatePiThread(int iterAmount, CountDownLatch latch) {
        this.iterAmount = iterAmount;
        this.latch = latch;
        random = ThreadLocalRandom.current();
    }

    @Override
    public void run() {
        for (int i = 0; i < iterAmount; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double distance = getDistanceFromCenter(x, y);
            if (distance <= RADIUS) {
                circleCount++;
            }
            totalCount++;
        }
        latch.countDown();
    }

    private double getDistanceFromCenter(double x1, double y1) {
        return Math.sqrt(Math.pow(CENTER_X - x1, 2) + Math.pow(CENTER_Y - y1, 2));
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCircleCount() {
        return circleCount;
    }
}
