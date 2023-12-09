package edu.hw9;

import edu.hw9.task1.Metrics;
import edu.hw9.task1.StatsCollector;
import edu.hw9.task2.FileByPredicateFinder;
import edu.hw9.task2.LargeDirectoryFinder;
import edu.hw9.task3.DfsMultiThreadSolver;
import edu.project2.Coordinate;
import edu.project2.Maze;
import edu.project2.generators.Generator;
import edu.project2.generators.PrimGenerator;
import edu.project2.solvers.Solver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class HW9Test {

    @Test
    public void testStatsCollector() {
        //given
        int threadsAmount = 5;
        int testThreadsAmount = 3;

        //when
        try (ExecutorService service = Executors.newFixedThreadPool(testThreadsAmount);
             StatsCollector collector = new StatsCollector(threadsAmount)) {

            service.submit(() -> {
                double[] data = {0.1, 0.05, 1.4, 5.1, 0.3};
                collector.push("task1", data);
            });
            service.submit(() -> {
                double[] data = {512.1, 499.5, 995.1123, 9865.11, 11};
                collector.push("task2", data);
            });
            service.submit(() -> {
                double[] data = {412.1, 119991.1, 199.1, 52.1, 40.3};
                collector.push("task1", data);
            });
            service.awaitTermination(5, TimeUnit.SECONDS);
            Map<String, Metrics> result = collector.stats();
            Metrics resultTask1 = result.get("task1");
            Metrics resultTask2 = result.get("task2");

            //then
            Metrics expectedResultTask1 = new Metrics(
                "task1",
                120701.65000000001,
                119991.1,
                0.05,
                12070.165,
                10
            );
            Metrics expectedResultTask2 = new Metrics(
                "task2",
                11882.8223,
                9865.11,
                11,
                2376.56446,
                5
            );
            assertThat(resultTask1.equals(expectedResultTask1)).isTrue();
            assertThat(resultTask2.equals(expectedResultTask2)).isTrue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLargeDirectoryFinder(@TempDir(cleanup = CleanupMode.ALWAYS) Path tempDir) {
        try {
            //given
            int filesAmount = 100;
            Path expectedResult = generateDirectoryTree(tempDir);

            //when
            LargeDirectoryFinder finder = new LargeDirectoryFinder();
            Path result = finder.find(tempDir, filesAmount);

            //then
            assertThat(result).isEqualTo(expectedResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path generateDirectoryTree(Path initial) throws IOException {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            int filesAmount = random.nextInt(80, 120);
            Path testDir = Path.of(initial.toAbsolutePath().toString(), "testDir" + i);
            createDirWithFiles(testDir, filesAmount);
            if (filesAmount >= 100) {

                return testDir;
            }
        }
        int filesAmount = 101;
        Path testDir = Path.of(initial.toAbsolutePath().toString(), "testDir50");
        createDirWithFiles(testDir, filesAmount);
        return testDir;
    }

    private void createDirWithFiles(Path name, int filesAmount) throws IOException {
        Path testDir = Files.createDirectory(name);
        for (int i = 0; i < filesAmount; i++) {
            Files.createFile(Path.of(testDir.toAbsolutePath().toString(), "test" + i));
        }
    }

    @Test
    public void testFileByPredicateFinder(@TempDir(cleanup = CleanupMode.ALWAYS) Path tempDir) {
        try {
            //given
            List<Path> dirs = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                dirs.add(Files.createDirectories(
                    Path.of(tempDir.toAbsolutePath().toString(), "try", "to", "find", "me", "again" + i)
                ));
            }
            Random random = new Random();
            int num = random.nextInt(5);
            Path fileToFind = Files.createFile(Path.of(dirs.get(num).toAbsolutePath().toString(), "fileToFind"));
            String largeString = "I'm the largest string you've ever seen!";
            Files.writeString(fileToFind, largeString);
            Predicate<Path> predicate1 = v -> v.getFileName().toString().endsWith("Find");
            Predicate<Path> predicate2 = v -> {
                try {
                    return Files.size(v) > 5;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            List<Predicate<Path>> preds = List.of(predicate1, predicate2);

            //when
            FileByPredicateFinder finder = new FileByPredicateFinder();
            Path result = finder.find(tempDir, preds);

            //then
            assertThat(result).isEqualTo(fileToFind);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDfsMultiThreadSolver() {
        //given
        int width = 35;
        int height = 35;
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(33, 33);

        //when
        Generator generator = new PrimGenerator();
        Maze maze = generator.generate(height, width);
        Solver solver = new DfsMultiThreadSolver();
        List<Coordinate> result = solver.solve(maze, start, end);

        //then
        assertThat(result).contains(start);
        assertThat(result).contains(end);
    }
}
