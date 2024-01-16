package edu.hw10.task2;

import java.lang.reflect.Proxy;

public class CacheProxy {

    private CacheProxy() {}

    public static <T> T create(T t, Class<? extends T> clazz) {
        return create(clazz, new CacheInvocationHandler(t));
    }

    public static <T> T create(Class<? extends T> clazz, CacheInvocationHandler handlerWithObject) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = clazz.getInterfaces();
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handlerWithObject);
    }
}
