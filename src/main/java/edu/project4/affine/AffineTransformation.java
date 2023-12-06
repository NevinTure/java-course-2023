package edu.project4.affine;

import edu.project4.Color;
import edu.project4.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AffineTransformation {

    private final List<AffineFactors> factorsList;
    private final Random random = ThreadLocalRandom.current();

    public AffineTransformation(int n) {
        factorsList = new ArrayList<>(n);
        generate(n);
    }

    private void generate(int n) {
        double a = 0;
        double b = 0;
        double d = 0;
        double e = 0;
        double c = 0;
        double f = 0;
        for (int i = 0; i < n; i++) {
            a = 100;
            while (((a * a + d * d) >= 1)
                || ((b * b + e * e) >= 1)
                || (a * a + b * b + d * d + e * e >= 1 + Math.pow(a * e - b * d, 2))) {
                a = random.nextDouble(-1, 1);
                b = random.nextDouble(-1, 1);
                d = random.nextDouble(-1, 1);
                e = random.nextDouble(-1, 1);
                c = random.nextDouble(-2.0, 2.0);
                f = random.nextDouble(-2.0, 2.0);
            }
            Color color = generateColor();
            factorsList.add(new AffineFactors(a, b, c, d, e, f, color));
        }
    }

    private Color generateColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    public AffineFactors getRandom(Random randomSupplier) {
        return factorsList.get(randomSupplier.nextInt(factorsList.size()));
    }

    public Point apply(Point point, AffineFactors factors) {
        double xt = point.x() * factors.a() + point.y() * factors.b() + factors.c();
        double yt = point.x() * factors.d() + point.y() * factors.e() + factors.f();
        return new Point(xt, yt);
    }
}
