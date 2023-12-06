package edu.project4.renderer;

import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.Point;
import edu.project4.Rect;
import edu.project4.affine.AffineFactors;
import edu.project4.affine.AffineTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.Random;

public class SingleThreadRenderer implements Renderer {

    private final int symmetry;
    private final Random RANDOM = new Random();

    public SingleThreadRenderer(int symmetry) {
        this.symmetry = symmetry;
    }

    public SingleThreadRenderer() {
        this.symmetry = 1;
    }

    @Override
    public void render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        AffineTransformation affine,
        int samples,
        short iterPerSample,
        long seed
    ) {
        RANDOM.setSeed(seed);
        for (int i = 0; i < samples; i++) {
            Point pw = randomPoint(world);

            for (short step = 0; step < iterPerSample; step++) {
                Transformation variation = variations.get(step % variations.size());
                AffineFactors factors = affine.getRandom(RANDOM);
                pw = affine.apply(pw, factors);
                pw = transform(pw, variation);

                double theta2 = 0.0;
                for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, s++) {
                    Point pwr = rotate(pw, theta2);
                    if (!world.contains(pwr)) {
                        continue;
                    }
                    pwr = calculatePosition(world, pwr, canvas);
                    int x = (int) pwr.x();
                    int y = (int) pwr.y();
                    if (canvas.contains(x, y)) {
                        Pixel pixel = canvas.pixel(x, y);
                        if (pixel.getHitCount() == 0) {
                            pixel.setColor(factors.color());
                        } else {
                            pixel.setColor(factors.color().divColor(2));
                        }
                        pixel.addHit();
                    }
                }
            }
        }
    }

    private Point randomPoint(Rect rect) {
        double x = RANDOM.nextDouble(rect.xMin(), rect.xMax());
        double y = RANDOM.nextDouble(rect.yMin(), rect.yMax());
        return new Point(x, y);
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

    private Point calculatePosition(Rect rect, Point point, FractalImage canvas) {
        int xs =  canvas.width() - (int) (Math.abs(((rect.xMax() - point.x())
            / (rect.xMax() - rect.xMin())) * canvas.width()));
        int ys = canvas.height() - (int) (Math.abs(((rect.yMax() - point.y())
            / (rect.yMax() - rect.yMin())) * canvas.height()));
        return new Point(xs, ys);
    }
}
