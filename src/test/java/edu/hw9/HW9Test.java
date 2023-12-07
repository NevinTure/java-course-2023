package edu.hw9;

import edu.hw9.task2.FileByPredicateFinder;
import edu.hw9.task2.LargeDirectoryFinder;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class HW9Test {

    @Test
    public void test() {
        Path path = Path.of("F:\\");
        LargeDirectoryFinder finder = new LargeDirectoryFinder();
        System.out.println(finder.find(path, 1000));
    }

    @Test
    public void test2() throws IOException {
        String origin = "F:\\SteamLibrary\\lol";
        for (int i = 0; i < 6001; i++) {
            Files.createFile(Path.of(origin, "file" + i));
        }
    }

    @Test public void test3() {
        Path path = Path.of("F:\\");
        Path file = Path.of("F:\\SteamLibrary\\lol", "loft.aga");
        Predicate<Path> predicate1 = v -> v.getFileName().toString().endsWith(".aga");
        Predicate<Path> predicate2 = v -> v.getFileName().toString().startsWith("loft");
        List<Predicate<Path>> predicateList = List.of(predicate1, predicate2);
        FileByPredicateFinder finder = new FileByPredicateFinder();
        System.out.println(finder.find(path, predicateList));
    }
}
