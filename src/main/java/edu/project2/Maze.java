package edu.project2;

public final class Maze {

    private final Cell[][] grid;
    private final int height;
    private final int width;

    public Maze(Cell[][] grid, int height, int width) {
        this.grid = grid;
        this.height = height;
        this.width = width;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
