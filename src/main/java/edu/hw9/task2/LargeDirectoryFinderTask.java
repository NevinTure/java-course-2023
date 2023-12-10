package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class LargeDirectoryFinderTask extends RecursiveTask<Path> {

    private final Path current;
    private final int dirBound;

    public LargeDirectoryFinderTask(Path current, int dirBound) {
        this.current = current;
        this.dirBound = dirBound;
    }

    @Override
    protected Path compute() {
        int fileCounter = 0;
        List<LargeDirectoryFinderTask> remainDirs = new ArrayList<>();
        try (Stream<Path> pathsStream = Files.list(current)) {
            List<Path> paths = pathsStream.toList();
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    LargeDirectoryFinderTask finder = new LargeDirectoryFinderTask(path, dirBound);
                    finder.fork();
                    remainDirs.add(finder);
                } else {
                    fileCounter++;
                }
            }
            if (fileCounter > dirBound) {
                return current;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path path;
        for (var finder : remainDirs) {
            path = finder.join();
            if (path != null) {
                return path;
            }
        }
        return null;
    }
}
