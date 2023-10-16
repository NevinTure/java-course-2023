package edu.hw3.tree_and_null;

import java.util.Comparator;
import java.util.Objects;

public class ComparatorWithNull<T> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if (Objects.equals(o1, o2)) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else {
            return 1;
        }
    }
}
