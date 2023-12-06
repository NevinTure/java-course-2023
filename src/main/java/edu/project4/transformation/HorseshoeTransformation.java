package edu.project4.transformation;

import edu.project4.Point;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = 1.0 / Math.sqrt(x * x + y * y);
        return new Point(
            r * (x - y) * (x + y),
            r * 2.0 * x * y
        );
    }
}
