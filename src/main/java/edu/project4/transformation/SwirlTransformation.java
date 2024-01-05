package edu.project4.transformation;

import edu.project4.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = x * x + y * y;
        double newX = x * Math.sin(r) - y * Math.cos(r);
        double newY = x * Math.cos(r) + y * Math.sin(r);
        return new Point(newX, newY);
    }
}
