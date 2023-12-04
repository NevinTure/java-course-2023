package edu.project4;

public record Rect(double x, double y, double width, double height) {
    boolean contains(Point p) {
        return (x<= p.x() && p.x() <= x + width)
            && (y<= p.y() && p.y() <= y + height);
    }
}
