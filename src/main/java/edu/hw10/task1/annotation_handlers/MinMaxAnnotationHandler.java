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
        return switch (obj) {
            case Integer i -> RANDOM.nextInt(ClassUtils.toIntMin(min), ClassUtils.toIntMax(max));
            case Byte i -> (byte) RANDOM.nextLong(ClassUtils.toByteMin(min), ClassUtils.toByteMax(max));
            case Short i -> (short) RANDOM.nextLong(ClassUtils.toShortMin(min), ClassUtils.toShortMax(max));
            case Long i -> RANDOM.nextLong(min, max);
            case Character i -> (char) RANDOM.nextLong(ClassUtils.toCharMin(min), ClassUtils.toCharMax(max));
            case Double i -> RANDOM.nextDouble(ClassUtils.toDoubleMax(min), ClassUtils.toDoubleMin(max));
            case Float i -> RANDOM.nextFloat(ClassUtils.toFloatMin(min), ClassUtils.toFloatMax(max));
            default -> false;
        };
    }
}
