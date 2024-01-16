package edu.hw10;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import lombok.Data;

@Data
public class Person {

    private final String name;
    private final Dog dog;
    private final String address;
    private final int age;

    public static Person create(@NotNull String name, @NotNull Dog dog, String address, @Min(10) @Max(40) int age) {
        return new Person(name, dog, address, age);
    }
}
