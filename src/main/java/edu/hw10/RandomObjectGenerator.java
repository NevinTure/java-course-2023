package edu.hw10;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
        if (fabricMethod.equals(CONSTRUCTOR)) {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Constructor<?> constructor = getAppropriateConstructor(constructors);
            Parameter[] params = constructor.getParameters();
            Object[] objParams = getRandomParams(params);
            try {
                return (T) constructor.newInstance(objParams);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
//            System.out.println(Arrays.toString(clazz.getDeclaredFields()));
//            System.out.println(constructor.getParameterCount());
//            System.out.println(Arrays.toString(constructors));
        }
//        switch (fabricMethod) {
//            case "constructor" -> {
//                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
//                Constructor<?> constructor = constructors[1];
//                Parameter[] parameters = constructor.getParameters();
//                Parameter parameter = parameters[1];
//                Object[] instParams = new Object[parameters.length];
//                Field field = clazz.getDeclaredField(parameter.getName());
//                Annotation[] annotations = field.getDeclaredAnnotations();
//                Class<?> type = parameter.getType();
//                if (type.isPrimitive()) {
//                    instParams[1] = generateRandomParam(type, annotations);
//                }
//            }
//        }
        return null;
    }

    private Constructor<?> getAppropriateConstructor(Constructor<?>[] constructors) {
        return Arrays
            .stream(constructors)
            .max(Comparator.comparing(Constructor::getParameterCount))
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
            preHandled = ClassUtils.getRandomPrimitive(param.getType());
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

    private Object applyHandlers(List<AnnotationHandler> handlers, Object preHandled) {
        for (AnnotationHandler handler : handlers) {
            preHandled = handler.handle(preHandled);
        }
        return preHandled;
    }

    public <T> T nextObject(Class<T> clazz) throws NoSuchFieldException {
        return nextObject(clazz, CONSTRUCTOR);
    }

    private Object generateRandomParam(Class<?> clazz, Annotation[] annotations) {
//        if (clazz.isPrimitive()) {
//
//        }
        return null;
    }

    private boolean isContainsAnnotation(String annotName, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(annotName)) {
                return true;
            }
        }
        return false;
    }
}
