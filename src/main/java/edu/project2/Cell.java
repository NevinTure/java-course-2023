package edu.project2;

import java.util.Objects;

public class Cell {
    private Type type;
    private Coordinate coord;

    public Cell(Type type, Coordinate coord) {
        this.type = type;
        this.coord = coord;
    }

    public Type getType() {
        return type;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return type == cell.type && Objects.equals(coord, cell.coord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, coord);
    }
}
