package edu.hw9.task2;

import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;

public class LargeDirectoryFinder {

    public Path find(Path initDir, int dirBound) {
        Path result;
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            LargeDirectoryFinderTask task = new LargeDirectoryFinderTask(initDir, dirBound);
            result = forkJoinPool.invoke(task);
        }
        return result;
    }
}
