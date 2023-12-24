package edu.project5;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Function;

public class NameAccessUtils {

    private final Class<?> clazz;
    private final String METHOD_NAME = "name";

    public NameAccessUtils(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getNameMethod() {
        try {
            return clazz.getDeclaredMethod(METHOD_NAME);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public MethodHandle getNameMethodHandle() {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class);
        try {
            return lookup.findVirtual(clazz, METHOD_NAME, mt);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Function<Student, String> getNameLambdaMetafactory() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(String.class);
        MethodType funcSignature = MethodType.methodType(Function.class);
        MethodType funcTypes = MethodType.methodType(Object.class, Object.class);
        MethodType dynamic = MethodType.methodType(String.class, Student.class);
        try {
            MethodHandle lambdaImpl = lookup.findVirtual(clazz, METHOD_NAME, type);
            return (Function<Student, String>) LambdaMetafactory.metafactory(
                    lookup,
                    "apply",
                    funcSignature,
                    funcTypes,
                    lambdaImpl,
                    dynamic
                )
                .getTarget()
                .invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
