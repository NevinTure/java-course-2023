package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileByPredicateFinderTask extends RecursiveTask<Path> {

    private final Path current;
    private final List<Predicate<Path>> predicates;

    public FileByPredicateFinderTask(Path current, List<Predicate<Path>> predicates) {
        this.current = current;
        this.predicates = predicates;
    }

    @Override
    protected Path compute() {
        if (!Files.isDirectory(current)) {
            for (var pred : predicates) {
                if (!pred.test(current)) {
                    return null;
                }
            }
            return current;
        }
        List<FileByPredicateFinderTask> remainTasks = new ArrayList<>();
        try (Stream<Path> pathsStream = Files.list(current)) {
            pathsStream.forEach(v -> {
                FileByPredicateFinderTask task = new FileByPredicateFinderTask(v, predicates);
                task.fork();
                remainTasks.add(task);
            });
            } catch (IOException e) {
                throw new RuntimeException(e);
        }
        Path path;
        for (var task : remainTasks) {
            path = task.join();
            if (path != null) {
                return path;
            }
        }
        return null;
    }
}
