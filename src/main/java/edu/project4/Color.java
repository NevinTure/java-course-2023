package edu.project4;

public record Color(int r, int g, int b) {

    public static final Color BLACK = new Color(0, 0, 0);

    public int ARGB() {
        return (255 << 24) | (r << 16) | (g << 8) | b;
    }
}
