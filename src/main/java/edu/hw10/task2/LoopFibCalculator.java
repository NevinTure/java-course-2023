package edu.hw10.task2;

public class LoopFibCalculator implements FibCalculator {

    private long fib(int number) {
        int a = 1;
        int b = 1;
        int temp;
        for (int i = 2; i < number; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }

    @Override
    public long fibAsLong(int number) {
        return fib(number);
    }

    @Override
    public String fibAsString(int number) {
        return String.valueOf(fib(number));
    }

    @Override
    public long[] fibAsArray(int number) {
        if (number == 1) {
            return new long[]{1};
        } else if (number == 2) {
            return new long[] {1, 1};
        }
        long[] result = new long[number];
        result[0] = 1;
        result[1] = 1;
        for (int i = 2; i < number; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }
        return result;
    }
}
