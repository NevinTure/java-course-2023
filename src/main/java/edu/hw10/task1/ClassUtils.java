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

    public static int toIntMax(long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            return (int) value;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static int toIntMin(long value) {
        if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
            return (int) value;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public static byte toByteMax(long value) {
        if (Byte.MIN_VALUE <= value && value <= Byte.MAX_VALUE) {
            return (byte) value;
        } else {
            return Byte.MAX_VALUE;
        }
    }

    public static byte toByteMin(long value) {
        if (Byte.MIN_VALUE <= value && value <= Byte.MAX_VALUE) {
            return (byte) value;
        } else {
            return Byte.MIN_VALUE;
        }
    }

    public static short toShortMax(long value) {
        if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE) {
            return (short) value;
        } else {
            return Short.MAX_VALUE;
        }
    }

    public static short toShortMin(long value) {
        if (Short.MIN_VALUE <= value && value <= Short.MAX_VALUE) {
            return (short) value;
        } else {
            return Short.MIN_VALUE;
        }
    }

    public static char toCharMax(long value) {
        if (Character.MIN_VALUE <= value && value <= Character.MAX_VALUE) {
            return (char) value;
        } else {
            return Character.MAX_VALUE;
        }
    }

    public static char toCharMin(long value) {
        if (Character.MIN_VALUE <= value && value <= Character.MAX_VALUE) {
            return (char) value;
        } else {
            return Character.MIN_VALUE;
        }
    }

    public static float toFloatMax(long value) {
        if (Float.MIN_VALUE <= value) {
            return (float) value;
        } else {
            return Float.MAX_VALUE;
        }
    }

    public static float toFloatMin(long value) {
        if (Float.MIN_VALUE <= value) {
            return (float) value;
        } else {
            return Float.MIN_VALUE;
        }
    }

    public static double toDoubleMax(long value) {
        if (Double.MIN_VALUE <= value) {
            return (double) value;
        } else {
            return Double.MAX_VALUE;
        }
    }

    public static double toDoubleMin(long value) {
        if (Double.MIN_VALUE <= value) {
            return (double) value;
        } else {
            return Double.MIN_VALUE;
        }
    }
}
