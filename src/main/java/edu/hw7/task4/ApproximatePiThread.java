package edu.hw7.task4;

import java.util.concurrent.ThreadLocalRandom;

public class ApproximatePiThread extends Thread {

    private final ThreadLocalRandom random;
    private final int iterAmount;
    private int totalCount;
    private int circleCount;

    public ApproximatePiThread(int iterAmount) {
        this.iterAmount = iterAmount;
        random = ThreadLocalRandom.current();
    }

    @Override
    public void run() {
        double radius = 0.5;
        double centerX = 0.5;
        double centerY = 0.5;
        for (int i = 0; i < iterAmount; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double distance = getDistanceBetween(x, y, centerX, centerY);
            if (distance <= radius) {
                circleCount++;
            }
            totalCount++;
        }
    }

    private double getDistanceBetween(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCircleCount() {
        return circleCount;
    }
}
