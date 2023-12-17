package edu.hw10;

import java.lang.reflect.InvocationTargetException;

public class NotNullAnnotationHandler implements AnnotationHandler {

    private Class<?> clazz;

    public NotNullAnnotationHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handle(Object preHandled) {
        if (preHandled == null) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
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
