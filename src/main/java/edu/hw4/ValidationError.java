package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public class ValidationError {

    private final ErrorField field;

    private ValidationError(ErrorField field) {
        this.field = field;
    }

    public static Set<ValidationError> validate(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();
        errors.add(validateName(animal.name()));
        errors.add(validateSex(animal.sex()));
        errors.add(validateAge(animal.age()));
        errors.add(validateType(animal.type()));
        errors.add(validateHeight(animal.height()));
        errors.add(validateWeight(animal.weight()));
        errors.remove(null);
        return errors;
    }

    private static ValidationError validateName(String name) {
        if (name == null || !name.matches("\\w+")) {
            return new ValidationError(ErrorField.NAME);
        } else {
            return null;
        }
    }

    private static ValidationError validateType(Animal.Type type) {
        if (type == null) {
            return new ValidationError(ErrorField.TYPE);
        } else {
            return null;
        }
    }

    private static ValidationError validateSex(Animal.Sex sex) {
        if (sex == null) {
            return new ValidationError(ErrorField.SEX);
        } else {
            return null;
        }
    }

    private static ValidationError validateAge(int age) {
        if (age < 0) {
            return new ValidationError(ErrorField.AGE);
        } else {
            return null;
        }
    }

    private static ValidationError validateHeight(int height) {
        if (height <= 0) {
            return new ValidationError(ErrorField.HEIGHT);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return field.toString();
    }

    private static ValidationError validateWeight(int weight) {
        if (weight <= 0) {
            return new ValidationError(ErrorField.WEIGHT);
        } else {
            return null;
        }
    }

    enum ErrorField {
        NAME, TYPE, SEX, AGE, HEIGHT, WEIGHT
    }

}
