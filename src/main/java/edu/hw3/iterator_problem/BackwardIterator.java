package edu.hw3.iterator_problem;

import java.util.Collection;
import java.util.List;

public class BackwardIterator<T> {
    private int currPos;
    private final List<T> list;

    public BackwardIterator(Collection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null!");
        }
        this.list = collection.stream().toList();
        currPos = collection.size() - 1;
    }

    public boolean hasPrev() {
        return currPos >= 0;
    }

    public T prev() {
        if (hasPrev()) {
            return list.get(currPos--);
        } else {
            return null;
        }
    }
}
