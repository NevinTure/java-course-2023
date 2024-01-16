package edu.hw7;

import java.util.stream.LongStream;

public class Task2 {

    private Task2() {
    }

    public static long factorial(long base) {
        return LongStream
            .iterate(1, v -> v <= base, v -> v + 1)
            .parallel()
            .reduce(1, (v1, v2) -> v1 * v2);
    }
}
