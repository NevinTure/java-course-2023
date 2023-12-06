package edu.project4.util;

import java.util.Random;

public class RandomUtils {

    private static final ThreadLocal<Random> RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(Random::new);

    private RandomUtils() {
    }

    public static Random threadLocalRandom(long seed) {
        Random random = RANDOM_THREAD_LOCAL.get();
        random.setSeed(seed);
        return random;
    }
}
