package edu.project4;

import java.util.concurrent.locks.ReentrantLock;

public class Pixel {

    private Color color;
    private int hitCount;
    private double normal;
    private final ReentrantLock lock;

    public Pixel() {
        this.lock = new ReentrantLock();
    }

    public Pixel(Color color) {
        this();
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void addHit() {
        hitCount++;
    }

    public int getHitCount() {
        return hitCount;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public ReentrantLock getLock() {
        return lock;
    }
}
