package edu.project4;

public class Pixel {

    private Color color;
    private int hitCount;
    public Pixel() {
    }

    public Pixel(Color color) {
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
}
