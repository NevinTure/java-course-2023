package edu.hw7.task4;

import java.util.Random;

public class PiApproximator {

    private final static double RADIUS = 0.5;
    private final static double CENTER_X = 0.5;
    private final static double CENTER_Y = 0.5;
    private final Random random;
    private int totalCount;
    private int circleCount;

    public PiApproximator() {
        random = new Random();
    }

    @SuppressWarnings("MagicNumber")
    public double approximate(int n) {
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            double distance = getDistanceBetween(x, y, CENTER_X, CENTER_Y);
            if (distance <= RADIUS) {
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
