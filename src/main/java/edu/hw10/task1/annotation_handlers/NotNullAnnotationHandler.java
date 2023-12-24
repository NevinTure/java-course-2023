package edu.hw10.task1.annotation_handlers;

import edu.hw10.task1.RandomObjectGenerator;
import java.lang.reflect.InvocationTargetException;

public class NotNullAnnotationHandler implements AnnotationHandler {

    private final Class<?> clazz;

    public NotNullAnnotationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(Object preHandled) {
        if (preHandled == null) {
            try {
                if (clazz.isRecord()) {
                    RandomObjectGenerator rog = new RandomObjectGenerator();
                    return rog.nextObject(clazz);
                } else {
                    return clazz.getDeclaredConstructor().newInstance();
                }
            } catch (NoSuchMethodException
                     | InstantiationException
                     | IllegalAccessException
                     | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return preHandled;
    }
}
