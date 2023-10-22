package edu.project2;

import java.util.Objects;

public record Coordinate(int row, int col) {
    public static final Coordinate SOUTH = new Coordinate(1, 0);
    public static final Coordinate NORTH = new Coordinate(-1, 0);
    public static final Coordinate WEST = new Coordinate(0, -1);
    public static final Coordinate EAST = new Coordinate(0,  1);

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
