package edu.hw10;

import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MinMaxAnnotationHandler implements AnnotationHandler {

    private long min;
    private long max;
    private static final Random RANDOM = ThreadLocalRandom.current();

    public MinMaxAnnotationHandler() {
        min = Long.MIN_VALUE;
        max = Long.MAX_VALUE;
    }

    public void setMin(Parameter param) {
        Min minAnnot = param.getAnnotation(Min.class);
        min = minAnnot.value();
    }

    public void setMax(Parameter param) {
        Max maxAnnot = param.getAnnotation(Max.class);
        max = maxAnnot.value();
    }

    @Override
    public Object handle(Object preHandled) {
        if (preHandled == null) {
            return RANDOM.nextLong(min, max);
        } else {
            return getNewValue(preHandled);
        }
    }

    private Object getNewValue(Object obj) {
        switch (obj) {
            case Integer i -> {
                if ((int) obj < min || (int) obj > max) {
                    return RANDOM.nextInt((int) min, (int) max);
                } else {
                    return obj;
                }
            }
            case Byte i -> {
                if ((byte) obj < min || (byte) obj > max) {
                    return (byte) RANDOM.nextLong(min, max);
                } else {
                    return obj;
                }
            }
            case Short i -> {
                if ((short) obj < min || (short) obj > max) {
                    return (short) RANDOM.nextLong(min, max);
                } else {
                    return obj;
                }
            }
            case Long i -> {
                if ((long) obj < min || (long) obj > max) {
                    return RANDOM.nextLong(min, max);
                } else {
                    return obj;
                }
            }
            case Character i -> {
                if ((char) obj < min || (char) obj > max) {
                    return (char) RANDOM.nextLong(min, max);
                } else {
                    return obj;
                }
            }
            case Double i -> {
                if ((double) obj < min || (double) obj > max) {
                    return RANDOM.nextDouble(min, max);
                } else {
                    return obj;
                }
            }
            case Float i -> {
                if ((float) obj < min || (float) obj > max) {
                    return RANDOM.nextFloat(min, max);
                } else {
                    return obj;
                }
            }
            default -> {
                return null;
            }
        }
    }
}
