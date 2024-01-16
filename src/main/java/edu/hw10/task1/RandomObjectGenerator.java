package edu.hw10.task1;

import edu.hw10.task1.annotation_handlers.AnnotationHandler;
import edu.hw10.task1.annotation_handlers.MinMaxAnnotationHandler;
import edu.hw10.task1.annotation_handlers.NotNullAnnotationHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RandomObjectGenerator {

    private static final String CONSTRUCTOR = "constructor";

    public RandomObjectGenerator() {
    }

    public <T> T nextObject(Class<T> clazz, String fabricMethod) {
        try {
            Parameter[] params;
            Object[] objParams;
            if (fabricMethod.equals(CONSTRUCTOR)) {
                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                Constructor<?> constructor = getAppropriateConstructor(constructors);
                params = constructor.getParameters();
                objParams = getRandomParams(params);
                return (T) constructor.newInstance(objParams);
            } else {
                Method fabric = getAppropriateFabric(clazz, fabricMethod);
                params = fabric.getParameters();
                objParams = getRandomParams(params);
                return (T) fabric.invoke(null, objParams);
            }
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T nextObject(Class<T> clazz) {
        return nextObject(clazz, CONSTRUCTOR);
    }

    private Constructor<?> getAppropriateConstructor(Constructor<?>[] constructors) {
        return Arrays
            .stream(constructors)
            .max(Comparator.comparing(Constructor::getParameterCount))
            .orElse(null);
    }

    private Method getAppropriateFabric(Class<?> clazz, String fabricMethod) {
        return Arrays
            .stream(clazz.getDeclaredMethods())
            .filter(v -> v.getName().contains(fabricMethod))
            .findFirst()
            .orElse(null);
    }

    private Object[] getRandomParams(Parameter[] params) {
        Object[] objParams = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            objParams[i] = getRandomObjectOfType(params[i]);
        }
        return objParams;
    }

    private Object getRandomObjectOfType(Parameter param) {
        Object preHandled;
        if (param.getType().isPrimitive()) {
            preHandled = ClassUtils.getAsPrimitive(param.getType());
        } else {
            preHandled = null;
        }
        if (param.getAnnotations().length > 0) {
            List<AnnotationHandler> handlers = getHandlers(param);
            preHandled = applyHandlers(handlers, preHandled);
        }
        return preHandled;
    }

    private List<AnnotationHandler> getHandlers(Parameter param) {
        Annotation[] annotations = param.getAnnotations();
        List<AnnotationHandler> handlers = new ArrayList<>(annotations.length);
        MinMaxAnnotationHandler minMax = null;
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            switch (annotationClass.getSimpleName()) {
                case "Min" -> {
                    if (minMax == null) {
                        minMax = new MinMaxAnnotationHandler();
                        minMax.setMin(param);
                        handlers.add(minMax);
                    } else {
                        minMax.setMin(param);
                    }
                }
                case "Max" -> {
                    if (minMax == null) {
                        minMax = new MinMaxAnnotationHandler();
                        minMax.setMax(param);
                        handlers.add(minMax);
                    } else {
                        minMax.setMax(param);
                    }
                }
                case "NotNull" -> handlers.add(new NotNullAnnotationHandler(param.getType()));
                default -> handlers.size();
            }
        }
        return handlers;
    }

    @SuppressWarnings("ParameterAssignment")
    private Object applyHandlers(List<AnnotationHandler> handlers, Object preHandled) {
        for (AnnotationHandler handler : handlers) {
            preHandled = handler.handle(preHandled);
        }
        return preHandled;
    }
}
