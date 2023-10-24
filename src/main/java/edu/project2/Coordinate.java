package edu.project2;

import java.util.Objects;

public record Coordinate(int row, int col) {

    public Coordinate {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
