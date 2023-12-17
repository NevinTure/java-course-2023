package edu.hw10;

import java.lang.reflect.Parameter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MaxAnnotationHandler implements AnnotationHandler {

    private final long max;
    private static final Random RANDOM = ThreadLocalRandom.current();

    public MaxAnnotationHandler(Parameter param) {
        this.max = getMax(param);
    }

    private long getMax(Parameter param) {
        Max maxAnnot = param.getAnnotation(Max.class);
        if (maxAnnot != null) {
            return maxAnnot.value();
        }
        return Long.MAX_VALUE;
    }

    @Override
    public Object handle(Object preHandled) {
        if (preHandled == null) {
            return RANDOM.nextLong(0, max);
        } else {
            if ((long) preHandled > max) {
                return RANDOM.nextLong(max);
            } else {
                return RANDOM.nextLong((long) preHandled, max);
            }
        }
    }
}
