package edu.hw9.task2;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public class FileByPredicateFinder {

    public Path find(Path initDir, List<Predicate<Path>> predicates) {
        Path result;
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            FileByPredicateFinderTask task = new FileByPredicateFinderTask(initDir, predicates);
            result = forkJoinPool.invoke(task);
        }
        return result;
    }
}
