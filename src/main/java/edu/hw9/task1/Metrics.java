package edu.hw9.task1;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Metrics {

    private final String name;
    private final ReentrantLock lock;
    private double sum;
    private double max;
    private double min;
    private double average;
    private double entriesAmount;

    public Metrics(String name) {
        this.name = name;
        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;
        this.lock = new ReentrantLock();
    }

    public String getName() {
        return name;
    }

    public Metrics(String name, double sum, double max, double min, double average, double entriesAmount) {
        this.name = name;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.average = average;
        this.entriesAmount = entriesAmount;
        this.lock = new ReentrantLock();
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getEntriesAmount() {
        return entriesAmount;
    }

    public void setEntriesAmount(double entriesAmount) {
        this.entriesAmount = entriesAmount;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Metrics metrics = (Metrics) o;
        return Double.compare(sum, metrics.sum) == 0 && Double.compare(max, metrics.max) == 0
            && Double.compare(min, metrics.min) == 0 && Double.compare(average, metrics.average) == 0
            && Double.compare(entriesAmount, metrics.entriesAmount) == 0 && Objects.equals(name, metrics.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sum, max, min, average, entriesAmount);
    }

    @Override public String toString() {
        return "Metrics{"
            + "name='" + name + '\''
            + ", sum=" + sum
            + ", max=" + max
            + ", min=" + min
            + ", average=" + average
            + ", entriesAmount=" + entriesAmount
            + '}';
    }
}
