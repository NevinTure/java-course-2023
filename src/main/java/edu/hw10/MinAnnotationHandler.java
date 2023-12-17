package edu.hw10;

import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MinAnnotationHandler implements AnnotationHandler {

    private final long min;
    private static final Random RANDOM = ThreadLocalRandom.current();

    public MinAnnotationHandler(Parameter param) {
        this.min = getMin(param);
    }

    private long getMin(Parameter param) {
        Min minAnnot = param.getAnnotation(Min.class);
        if (minAnnot != null) {
            return minAnnot.value();
        }
        return Long.MIN_VALUE;
    }

    @Override
    public Object handle(Object preHandled) {
        if (preHandled == null) {
            return RANDOM.nextLong(min, Long.MAX_VALUE);
        } else {
            if ((long) preHandled < min) {
                return RANDOM.nextLong(min, Long.MAX_VALUE);
            } else {
                return preHandled;
            }
        }
    }
}
