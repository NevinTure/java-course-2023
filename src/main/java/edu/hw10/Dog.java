package edu.hw10;

import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public record Dog(String name, short age, byte weight) {

    public Dog(@NotNull String name, @Min(1) short age, @Min(1) byte weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }
}
