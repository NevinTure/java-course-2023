package edu.project4;

public class SphereTransformation implements Transformation {

    public static final Color color = new Color(0, 255, 0);

    @Override
    public Color paint() {
        return color;
    }

    @Override
    public Point apply(Point point) {
        double xt = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double yt = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(xt, yt);
    }
}
