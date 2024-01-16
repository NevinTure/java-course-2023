package edu.hw6.task3;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Objects;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {

    default AbstractFilter and(AbstractFilter other) {
        Objects.requireNonNull(other);
        return (t) -> accept(t) && other.accept(t);
    }

    default AbstractFilter or(AbstractFilter other) {
        Objects.requireNonNull(other);
        return (t) -> accept(t) || other.accept(t);
    }

    default AbstractFilter negate() {
        return (t) -> !accept(t);
    }
}
