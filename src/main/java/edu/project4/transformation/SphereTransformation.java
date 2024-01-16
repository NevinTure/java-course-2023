package edu.project4.transformation;

import edu.project4.Point;

public class SphereTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        double xt = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double yt = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(xt, yt);
    }
}
