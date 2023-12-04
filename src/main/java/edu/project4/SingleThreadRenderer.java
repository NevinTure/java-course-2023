package edu.project4;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadRenderer implements Renderer {

    private final int symmetry;

    public SingleThreadRenderer(int symmetry) {
        this.symmetry = symmetry;
    }

    public SingleThreadRenderer() {
        this.symmetry = 1;
    }

    private final Random random = ThreadLocalRandom.current();
    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed
    ) {
        random.setSeed(seed);
        for (int i = 0; i < samples; i++) {
            Point pw = randomPoint(world);

            for (short step = 0; step < iterPerSample; step++) {
                Transformation variation = randomTrans(variations);

                pw = transform(pw, variation);

                double theta2 = 0.0;
                for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, s++) {
                    Point pwr = rotate(pw, theta2);
                    if (!world.contains(pwr)) {
                        continue;
                    }
                    Pixel pixel = calculatePosition(world, pwr, canvas);
                    pixel.addHit();
                }
            }
        }
        return canvas;
    }

    private Point randomPoint(Rect rect) {
        double x = random.nextDouble(rect.x(), rect.x() + rect.width());
        double y = random.nextDouble(rect.y(), rect.y() + rect.height());
        return new Point(x, y);
    }

    private Transformation randomTrans(List<Transformation> variations) {
        return variations.get(random.nextInt(0, variations.size()));
    }

    private Point transform(Point point, Transformation transformation) {
        return transformation.apply(point);
    }

    private Point rotate(Point point, double theta) {
        double xr = point.x() * StrictMath.cos(theta)
            - point.y() * StrictMath.sin(theta);
        double yr = point.x()  * StrictMath.sin(theta)
            + point.y() * StrictMath.cos(theta);
        return new Point(xr, yr);
    }

    private Pixel calculatePosition(Rect rect, Point point, FractalImage canvas) {
        int xs = (int) (canvas.width() / rect.width() * point.x());
        int ys = (int) (canvas.height() / rect.height() * point.y());
        return canvas.pixel(xs, ys);
    }
}
