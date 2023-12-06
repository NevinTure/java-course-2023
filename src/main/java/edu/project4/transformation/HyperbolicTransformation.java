package edu.project4.transformation;

import edu.project4.Point;

public class HyperbolicTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = Math.sqrt(x * x + y * y);
        double theta = Math.atan2(y, x);
        return new Point(
            Math.sin(theta) / r,
            r * Math.cos(theta)
        );
    }
}
