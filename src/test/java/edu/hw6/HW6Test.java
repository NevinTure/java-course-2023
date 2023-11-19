package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import edu.hw6.task1.DiskMap;
import edu.hw6.task2.FileCloner;
import edu.hw6.task4.OutputStreamComposition;
import edu.hw6.task5.HackerNews;
import edu.hw6.task6.PortInfo;
import edu.hw6.task6.PortInfoUtil;
import edu.hw6.task6.PortScanner;
import edu.hw6.task6.Protocol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static edu.hw6.task3.Filters.extensionMatches;
import static edu.hw6.task3.Filters.globMatches;
import static edu.hw6.task3.Filters.largerThan;
import static edu.hw6.task3.Filters.magicNumber;
import static edu.hw6.task3.Filters.readable;
import static edu.hw6.task3.Filters.regexContains;
import static edu.hw6.task3.Filters.regularFile;
import static edu.hw6.task3.Filters.symbolicLink;
import static edu.hw6.task3.Filters.writable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class HW6Test {

    @Test
    public void testDiskMapStateRecovery(@TempDir Path tempDir) {
        //when
        Map<String, String> map1 = new DiskMap(tempDir);
        map1.put("1", "11");
        map1.put("2", "22");
        map1.put("3", "33");
        Map<String, String> map2 = new DiskMap(tempDir);

        //then
        assertThat(map1).containsAllEntriesOf(map2);
    }

    @Test
    public void testDiskMapPut(@TempDir Path tempDir) {
        //given
        Map<String, String> map = new DiskMap(tempDir);
        String key = "file1";
        String value = "test";

        //when/then
        map.put(key, value);
        Path file = null;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                String name = String.valueOf(path.getFileName());
                if (Objects.equals(name, key)) {
                    file = path;
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(file).isNotNull();
        assertThat(String.valueOf(file.getFileName())).isEqualTo(key);
        String fileData;
        try (Stream<String> lines = Files.lines(file)) {
            fileData = lines.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertThat(fileData).isEqualTo(value);
    }

    @Test
    public void testDiskMapRemove(@TempDir Path tempDir) {
        //given
        Map<String, String> map = new DiskMap(tempDir);
        String key = "file1";
        String value = "test";
        map.put(key, value);

        //when
        map.remove(key);

        //then
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                String name = String.valueOf(path.getFileName());
                assertThat(name).isNotEqualTo(key);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDiskMapGetAndContainsKeyValue(@TempDir Path tempDir) {
        //given
        String name = "test";
        String text = "test case";
        Path path = Path.of(tempDir.toString(), name);
        try {
            Files.createFile(path);
            Files.writeString(path, text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        Map<String, String> map = new DiskMap(tempDir);

        //then
        assertThat(map.containsKey(name)).isTrue();
        assertThat(map.containsValue(text)).isTrue();
        assertThat(map.get(name)).isEqualTo(text);
    }

    @Test
    public void testDiskMapDownload(@TempDir Path tempDir) {
        //given
        DiskMap map = new DiskMap(tempDir);
        List<Path> paths = new ArrayList<>(5);
        List<String> names = new ArrayList<>(5);
        for (int i = 1; i <= 5; i++) {
            names.add("test" + i);
            Path path = Path.of(tempDir.toString(), "test" + i);
            try {
                paths.add(Files.createFile(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //when
        map.downloadAll(paths);

        //then
        assertThat(map.keySet()).containsExactlyInAnyOrderElementsOf(names);
    }

    @Test
    public void testDiskMapErrors(@TempDir Path tempDir) {
        //given
        Path anotherDir = Paths.get(tempDir.toString(), "another");
        Path anotherFile = Paths.get(anotherDir.toString(), "test_from_another_dir");

        try {
            Files.createDirectory(anotherDir);
            Files.createFile(anotherFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        DiskMap map = new DiskMap(tempDir);

        assertThatIllegalArgumentException().isThrownBy(() -> map.download(anotherDir));
        assertThatIllegalArgumentException().isThrownBy(() -> map.download(anotherFile));
    }

    @Test
    public void testCloneFileWithoutExtension(@TempDir Path tempDir) {
        //given
        String originalName = "test";
        Path originalFile = Paths.get(tempDir.toString(), originalName);
        try {
            Files.createFile(originalFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        FileCloner.cloneFile(originalFile);
        FileCloner.cloneFile(originalFile);
        FileCloner.cloneFile(originalFile);

        //then
        List<String> expectedNames = List.of("test", "test — копия", "test — копия (2)", "test — копия (3)");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                assertThat(expectedNames).contains(String.valueOf(path.getFileName()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCloneFileWithExtension(@TempDir Path tempDir) {
        //given
        String originalName = "test.txt";
        String copyFileName = "test — копия.txt";
        Path originalFile = Paths.get(tempDir.toString(), originalName);
        Path copyFile = Paths.get(tempDir.toString(), copyFileName);
        try {
            Files.createFile(originalFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        FileCloner.cloneFile(originalFile);
        FileCloner.cloneFile(originalFile);
        FileCloner.cloneFile(originalFile);
        FileCloner.cloneFile(copyFile);

        //then
        List<String> expectedNames = List.of(
            "test.txt",
            "test — копия.txt",
            "test — копия (2).txt",
            "test — копия (3).txt",
            "test — копия — копия.txt"
        );
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path path : stream) {
                assertThat(expectedNames).contains(String.valueOf(path.getFileName()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBasicFilters(@TempDir Path tempDir) {
        //given
        Path regular = Paths.get(tempDir.toString(), "regular.txt");
        Path folder = Paths.get(tempDir.toString(), "test");
        try {
            Files.createFile(regular);
            Files.createDirectory(folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        DirectoryStream.Filter<Path> filters = writable
            .and(readable)
            .and(regularFile)
            .and(symbolicLink.negate());
        List<Path> remainedFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir, filters)) {
            for (Path path : stream) {
                remainedFiles.add(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThat(remainedFiles).contains(regular);
        assertThat(remainedFiles).doesNotContain(folder);
    }

    @Test
    public void testExtensionAndGlobMatchesFilter(@TempDir Path tempDir) {
        //given
        Path matches1 = Paths.get(tempDir.toString(), "test.txt");
        Path nonMatches2 = Paths.get(tempDir.toString(), "test.doc");
        Path nonMatches1 = Paths.get(tempDir.toString(), "tester");
        Path matches2 = Paths.get(tempDir.toString(), "test.tx");
        try {
            Files.createFile(matches1);
            Files.createFile(matches2);
            Files.createFile(nonMatches1);
            Files.createFile(nonMatches2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        DirectoryStream.Filter<Path> filters = globMatches("test.*")
            .and(extensionMatches("doc").negate());
        List<Path> remainedFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir, filters)) {
            for (Path path : stream) {
                remainedFiles.add(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThat(remainedFiles).contains(matches1);
        assertThat(remainedFiles).contains(matches2);
        assertThat(remainedFiles).doesNotContain(nonMatches1);
        assertThat(remainedFiles).doesNotContain(nonMatches2);
    }

    @Test
    public void testRegexContainsAndLargerThanAndMagicNumber(@TempDir Path tempDir) {
        Path largeFile = Paths.get(tempDir.toString(), "large");
        Path regexFile = Paths.get(tempDir.toString(), "re@ex");
        Path magicFile = Paths.get(tempDir.toString(), "magic");
        byte[] magicNumbers = {104, 101, 108, 108, 111};
        Path nonMatches = Paths.get(tempDir.toString(), "test");
        try {
            Files.createFile(largeFile);
            Files.writeString(largeFile, "very laaaaaaaaaaaaarge string");

            Files.createFile(regexFile);
            Files.createFile(nonMatches);

            Files.createFile(magicFile);
            Files.writeString(magicFile, new String(magicNumbers));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //when
        DirectoryStream.Filter<Path> filters = largerThan(5)
            .or(regexContains(".*@.*"))
            .or(magicNumber(104, 101, 108, 108, 111));
        List<Path> remainedFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir, filters)) {
            for (Path path : stream) {
                remainedFiles.add(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        assertThat(remainedFiles).contains(largeFile);
        assertThat(remainedFiles).contains(regexFile);
        assertThat(remainedFiles).contains(magicFile);
        assertThat(remainedFiles).doesNotContain(nonMatches);
    }

    @Test
    public void testOutputStreamComposition(@TempDir Path tempDir) {
        //given
        String text = "Programming is learned by writing programs. ― Brian Kernighan";
        Path path = Paths.get(tempDir.toString(), "test");
        String filePath = path.toString();

        //when
        OutputStreamComposition.writeToFile(filePath, text);

        //then
        try (Stream<String> lines = Files.lines(path)) {
            String result = lines.collect(Collectors.joining());
            assertThat(result).isEqualTo(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHackerNewsTopStoriesParse() {
        //when
        long[] news = HackerNews.hackerNewsTopStories();

        //then
        assertThat(news).isNotEmpty();
    }

    @Test
    public void testHackerNewsTitle() {
        //given
        long storyId = 37570037;

        //when
        String title = HackerNews.news(storyId);

        //then
        String expectedResult = "JDK 21 Release Notes";
        assertThat(title).isEqualTo(expectedResult);
    }

    @Test
    public void testHackerNewsTitleError() {
        //given
        long storyId = 1000000000L;

        //then
        assertThatIllegalArgumentException().isThrownBy(() -> HackerNews.news(storyId));
    }

    @Test
    public void testPortScanner() {
        //given
        int firstPort = 24567;
        int lastPort = 24569;

        //when
        List<PortInfo> ports;
        PortScanner scanner;
        try (ServerSocket tcp24567 = new ServerSocket(24567);
             ServerSocket tcp24569 = new ServerSocket(24569);
             ServerSocket tcp30111 = new ServerSocket(30111);
             DatagramSocket udp44321 = new DatagramSocket(44321);
             DatagramSocket udp24567 = new DatagramSocket(24567);
             DatagramSocket udp24568 = new DatagramSocket(24568)
        ) {
            scanner = new PortScanner();
            scanner.scanPortInRange(firstPort, lastPort);
            ports = scanner.getOccupiedPorts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        List<PortInfo> expectedResult = List.of(
            new PortInfo(Protocol.TCP, 24567, PortInfoUtil.getByPortNum(24567)),
            new PortInfo(Protocol.TCP, 24569, PortInfoUtil.getByPortNum(24569)),
            new PortInfo(Protocol.UDP, 24567, PortInfoUtil.getByPortNum(24567)),
            new PortInfo(Protocol.UDP, 24568, PortInfoUtil.getByPortNum(24568))
        );
        String expectedOccupiedPorts = """
            Протокол  Порт   Сервис
            TCP       24567  N/A
            UDP       24567  N/A
            UDP       24568  N/A
            TCP       24569  N/A
            """;
        assertThat(ports).containsExactlyInAnyOrderElementsOf(expectedResult);
        assertThat(scanner.printOccupiedPorts()).isEqualTo(expectedOccupiedPorts);
    }
}
