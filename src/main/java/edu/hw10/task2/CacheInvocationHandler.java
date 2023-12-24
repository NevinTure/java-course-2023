package edu.hw10.task2;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class CacheInvocationHandler implements InvocationHandler {

    private final Object obj;
    private final Path cacheFile;
    private final Map<String, List<Object>> inMemoryStorage;

    public CacheInvocationHandler(Object obj) {
        this.obj = obj;
        this.inMemoryStorage = new HashMap<>();
        try {
            cacheFile = Files.createTempFile("cache", ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CacheInvocationHandler(Object obj, Path cacheFile) {
        this.obj = obj;
        this.cacheFile = cacheFile;
        this.inMemoryStorage = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(obj, args);
        String methodName = method.getName();
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cache = method.getAnnotation(Cache.class);
            if (cache.persist()) {
                String resultStr = getResultAsString(result);
                writeToCacheFile(methodName, resultStr);
            } else {
                writeToInMemoryCache(methodName, result);
            }
        }
        return result;
    }

    private String getResultAsString(Object result) {
        if (result.getClass().isArray()) {
            return switch (result.getClass().getSimpleName()) {
                case "int[]" -> Arrays.toString((int[]) result);
                case "boolean[]" -> Arrays.toString((boolean[]) result);
                case "long[]" -> Arrays.toString((long[]) result);
                case "byte[]" -> Arrays.toString((byte[]) result);
                case "short[]" -> Arrays.toString((short[]) result);
                case "float[]" -> Arrays.toString((float[]) result);
                case "double[]" -> Arrays.toString((double[]) result);
                case "char[]" -> Arrays.toString((char[]) result);
                default -> Arrays.toString((Object[]) result);
            };
        } else {
            return result.toString();
        }
    }

    private void writeToCacheFile(String methodName, String result) {
        try {
            Files.writeString(
                cacheFile,
                "%s:%s%n".formatted(methodName, result),
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToInMemoryCache(String methodName, Object result) {
        String key = "%s".formatted(methodName);
        inMemoryStorage.computeIfAbsent(key, k -> new ArrayList<>()).add(result);
    }
}
