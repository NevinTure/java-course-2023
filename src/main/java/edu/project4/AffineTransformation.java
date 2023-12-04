package edu.project4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AffineTransformation {

    private final List<AffineFactors> factorsList;
    private static final Random random = ThreadLocalRandom.current();

    private AffineTransformation(List<AffineFactors> factorsList) {
        this.factorsList = factorsList;
    }

    public static AffineTransformation generate(int n) {
        List<AffineFactors> factorsList = new ArrayList<>(n);
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
                c = random.nextDouble(-3.5, 3.5);
                f = random.nextDouble(-3.5, 3.5);
            }
            factorsList.add(new AffineFactors(a, b, c, d, e, f));
        }
        return new AffineTransformation(factorsList);
    }

    public Point applyRandom(Point point) {
        AffineFactors factors = factorsList.get(random.nextInt(factorsList.size()));
        double xt = point.x() * factors.a() + point.y() * factors.b() + factors.c();
        double yt = point.x() * factors.d() + point.y() * factors.e() + factors.f();
        return new Point(xt, yt);
    }
}
