package edu.project4;

import java.util.Arrays;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[][] data = new Pixel[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(data[i], new Pixel(Color.BLACK));
        }
        return new FractalImage(data, width, height);
    }

    boolean contains(int x, int y) {
        return false;
    }

    Pixel pixel(int x, int y) {
        return data[x][y];
    }
}
