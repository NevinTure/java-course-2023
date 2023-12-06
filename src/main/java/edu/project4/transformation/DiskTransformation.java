package edu.project4.transformation;

import edu.project4.Point;

public class DiskTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double r = Math.sqrt(x *x + y*y) * Math.PI;
        double theta = Math.atan2(y,x) / Math.PI;
        return new Point(
            theta * Math.sin(r),
            theta * Math.cos(r)
        );
    }
}
