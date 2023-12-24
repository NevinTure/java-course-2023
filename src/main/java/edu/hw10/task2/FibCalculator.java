package edu.hw10.task2;

public interface FibCalculator {
    @Cache
    long fibAsLong(int number);

    @Cache(persist = true)
    String fibAsString(int number);

    @Cache(persist = true)
    long[] fibAsArray(int number);
}
