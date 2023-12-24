package edu.hw10.task1.annotation_handlers;

import edu.hw10.task1.ClassUtils;
import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
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

    @SuppressWarnings("CyclomaticComplexity")
    private Object getNewValue(Object obj) {
        Object result;
        switch (obj) {
            case Integer i -> {
                if ((int) obj < min || (int) obj > max) {
                    result = RANDOM.nextInt(ClassUtils.toIntMin(min), ClassUtils.toIntMax(max));
                } else {
                    result = obj;
                }
            }
            case Byte i -> {
                if ((byte) obj < min || (byte) obj > max) {
                    result = (byte) RANDOM.nextLong(ClassUtils.toByteMin(min), ClassUtils.toByteMax(max));
                } else {
                    result = obj;
                }
            }
            case Short i -> {
                if ((short) obj < min || (short) obj > max) {
                    result = (short) RANDOM.nextLong(ClassUtils.toShortMin(min), ClassUtils.toShortMax(max));
                } else {
                    result = obj;
                }
            }
            case Long i -> {
                if ((long) obj < min || (long) obj > max) {
                    result = RANDOM.nextLong(min, max);
                } else {
                    result = obj;
                }
            }
            case Character i -> {
                if ((char) obj < min || (char) obj > max) {
                    result = (char) RANDOM.nextLong(ClassUtils.toCharMin(min), ClassUtils.toCharMax(max));
                } else {
                    result = obj;
                }
            }
            case Double i -> {
                if ((double) obj < min || (double) obj > max) {
                    result = RANDOM.nextDouble(ClassUtils.toDoubleMax(min), ClassUtils.toDoubleMin(max));
                } else {
                    result = obj;
                }
            }
            case Float i -> {
                if ((float) obj < min || (float) obj > max) {
                    result = RANDOM.nextFloat(ClassUtils.toFloatMin(min), ClassUtils.toFloatMax(max));
                } else {
                    result = obj;
                }
            }
            default -> {
                result = null;
            }
        }
        return result;
    }
}
