package edu.hw4;

import java.util.Objects;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    Boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    @SuppressWarnings("MagicNumber")
    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        return age == animal.age && height == animal.height && weight == animal.weight
            && Objects.equals(name, animal.name) && type == animal.type && sex == animal.sex
            && Objects.equals(bites, animal.bites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, sex, age, height, weight, bites);
    }
}
