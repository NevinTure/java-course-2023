package edu.hw7.task4;

import java.security.SecureRandom;

public class PiApproximator {

    private final SecureRandom random;
    private int totalCount;
    private int circleCount;

    public PiApproximator() {
        random = new SecureRandom();
    }

    public double approximate(int n) {
        double radius = 0.5;
        double centerX = 0.5;
        double centerY = 0.5;
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double distance = getDistanceBetween(x, y, centerX, centerY);
            if (distance <= radius) {
                circleCount++;
            }
            totalCount++;
        }
        return (double) (4 * circleCount) / totalCount;
    }

    private double getDistanceBetween(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
