package edu.project4;

public record Rect(double xMin, double yMin, double xMax, double yMax) {

    public Rect {
        if (xMax == xMin
                || yMin == yMax
                || xMin > xMax
                || yMin > yMax) {
            throw new IllegalArgumentException();
        }
    }


    public boolean contains(Point p) {
        return (xMin <= p.x() && p.x() <= xMax)
            && (yMin <= p.y() && p.y() <= yMax);
    }
}
