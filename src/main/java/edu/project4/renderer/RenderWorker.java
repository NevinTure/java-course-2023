package edu.project4.renderer;

import edu.project4.FractalImage;
import edu.project4.Pixel;
import edu.project4.Point;
import edu.project4.Rect;
import edu.project4.affine.AffineFactors;
import edu.project4.affine.AffineTransformation;
import edu.project4.transformation.Transformation;
import edu.project4.util.RandomUtils;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class RenderWorker implements Renderer {

    private final Lock lock;
    private final int symmetry;
    private Random random;

    public RenderWorker(int symmetry, Lock lock)  {
        this.lock = lock;
        this.symmetry = symmetry;
    }

    public RenderWorker(Lock lock) {
        this.lock = lock;
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
        random = RandomUtils.threadLocalRandom(seed);
        for (int i = 0; i < samples; i++) {
            Point pw = randomPoint(world);

            for (short step = 0; step < iterPerSample; step++) {
                Transformation variation = variations.get(step % variations.size());
                AffineFactors factors = affine.getRandom(random);
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
                        lock.lock();
                        try {
                            if (pixel.getHitCount() == 0) {
                                pixel.setColor(factors.color());
                            } else {
                                pixel.setColor(factors.color().divColor(2));
                            }
                            pixel.addHit();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            }
        }
    }

    private Point randomPoint(Rect rect) {
        double x = random.nextDouble(rect.xMin(), rect.xMax());
        double y = random.nextDouble(rect.yMin(), rect.yMax());
        return new Point(x, y);
    }

    private Point transform(Point point, Transformation transformation) {
        return transformation.apply(point);
    }

    private Point rotate(Point point, double theta) {
        double xr = point.x() * Math.cos(theta)
            - point.y() * Math.sin(theta);
        double yr = point.x()  * Math.sin(theta)
            + point.y() * Math.cos(theta);
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
