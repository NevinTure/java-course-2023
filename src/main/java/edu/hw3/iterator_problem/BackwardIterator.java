package edu.hw3.iterator_problem;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BackwardIterator<T> implements Iterator<T> {
    private int currPos;
    private final List<T> list;

    public BackwardIterator(Collection<T> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null!");
        }
        this.list = collection.stream().toList();
        currPos = collection.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currPos >= 0;
    }

    @Override
    public T next() {
        if (hasNext()) {
            return list.get(currPos--);
        } else {
            return null;
        }
    }
}
