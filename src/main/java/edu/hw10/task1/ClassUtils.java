package edu.hw10.task1;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ClassUtils {

    private final static Random RANDOM = ThreadLocalRandom.current();

    private ClassUtils() {
    }


    @SuppressWarnings("MagicNumber")
    public static Object getRandomPrimitive(Class<?> primitive) {
        return switch (primitive.getSimpleName()) {
            case "boolean" -> RANDOM.nextBoolean();
            case "byte" -> (byte) RANDOM.nextInt(-128, 128);
            case "short" -> (short) RANDOM.nextInt(-32768, 32768);
            case "int" -> RANDOM.nextInt();
            case "long" -> RANDOM.nextLong();
            case "float" -> RANDOM.nextFloat();
            case "double" -> RANDOM.nextDouble();
            case "char" -> (char) RANDOM.nextInt(0, 65536);
            default -> null;
        };
    }
}
