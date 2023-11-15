package edu.project3;

import edu.project3.log_entry.Format;
import edu.project3.log_entry.HttpMethod;
import edu.project3.log_entry.HttpStatus;
import edu.project3.log_entry.NginxLogEntry;
import edu.project3.log_entry.Request;
import edu.project3.log_printer.LogPrinter;
import edu.project3.log_util.ArgumentsParser;
import edu.project3.log_util.LogParser;
import edu.project3.log_util.PathResolver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LogsHandlerTest {

    private static Stream<Arguments> wrongArgs() {
        return Stream.of(
            Arguments.of(new String[] {"--path", sep("/*.txt"), "--from", "2010-10-11", "--to", "2012-01-01",
                "--format",
                "wrong format"}, IllegalArgumentException.class),
            Arguments.of(new String[] {"--path"}, RuntimeException.class),
            Arguments.of(new String[] {"--path", sep("/*.txt"), "--from", "wrong format"}, DateTimeParseException.class)
        );
    }

    private static Stream<Arguments> logParserParams() {
        return Stream.of(
            Arguments.of(
                "85.214.47.178 - - [17/May/2015:12:05:35 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 337 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"",
                new NginxLogEntry(
                    "85.214.47.178",
                    "-",
                    OffsetDateTime.of(2015, 5, 17, 12, 5, 35, 0, ZoneOffset.UTC),
                    new Request(HttpMethod.GET, "/downloads/product_2", "HTTP/1.1"),
                    new HttpStatus(404),
                    337,
                    "-",
                    "Debian APT-HTTP/1.3 (0.9.7.9)"
                )
            ),
            Arguments.of(
                "87.85.173.82 - - [17/May/2015:15:05:07 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 325 \"-\" \"Wget/1.13.4 (linux-gnu)\"",
                new NginxLogEntry(
                    "87.85.173.82",
                    "-",
                    OffsetDateTime.of(2015, 5, 17, 15, 5, 7, 0, ZoneOffset.UTC),
                    new Request(HttpMethod.GET, "/downloads/product_1", "HTTP/1.1"),
                    new HttpStatus(200),
                    325,
                    "-",
                    "Wget/1.13.4 (linux-gnu)"
                )
            )
        );
    }

    private final static String TEST_FOLDER = sep("src/test/java/edu/project3/test_logs");

    @Test
    public void testArgumentParser() {
        //given
        Path testFolder = Path.of(TEST_FOLDER + sep("/args_parser")).toAbsolutePath();
        Format format;
        LocalDate from;
        LocalDate to;
        List<String> paths;
        String[] args =
            {"--path", testFolder + sep("/*"), "--from", "2010-10-11", "--to", "2012-01-01", "--format", "adoc"};

        //when
        List<Path> tempFiles = new ArrayList<>(5);
        try {
            for (int i = 1; i < 4; i++) {
                Path path = Files.createTempFile(testFolder, "log" + i, ".txt");
                tempFiles.add(path);

            }
            for (int i = 4; i < 6; i++) {
                tempFiles.add(Files.createTempFile(testFolder, "log" + i, ".data"));
            }
            format = ArgumentsParser.parseFormat(args);
            from = ArgumentsParser.parseFrom(args);
            to = ArgumentsParser.parseTo(args);
            paths = ArgumentsParser.parsePaths(args);
            for (Path path : tempFiles) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //then
        List<String> expectedPaths = tempFiles.stream().map(Path::toString).toList();
        assertThat(format).isEqualTo(Format.ADOC);
        assertThat(from).isEqualTo(LocalDate.of(2010, 10, 11));
        assertThat(to).isEqualTo(LocalDate.of(2012, 1, 1));
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expectedPaths);
    }

    @ParameterizedTest
    @MethodSource("wrongArgs")
    public void testArgumentsParserFailed(String[] args, Class<? extends Throwable> exceptionClass) {
        //then
        assertThatExceptionOfType(exceptionClass).isThrownBy(() -> {
            ArgumentsParser.parsePaths(args);
            ArgumentsParser.parseFormat(args);
            ArgumentsParser.parseFrom(args);
            ArgumentsParser.parseTo(args);
        });
    }

    @Test
    public void testPathResolver() {
        //given
        String rawPath = sep("src/test/**/test_logs/*");

        //when
        List<String> paths = PathResolver.get(rawPath);

        //then
        List<String> expectedResult = List.of(
            sep("src/test/java/edu/project3/test_logs/logs.txt"),
            sep("src/test/java/edu/project3/test_logs/logs2.txt"),
            sep("src/test/java/edu/project3/test_logs/logs3.txt")
        );
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("logParserParams")
    public void testLogParser(String rawLogEntry, NginxLogEntry expectedResult) {
        //when
        NginxLogEntry result = LogParser.parse(rawLogEntry);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testStatistics() {
        //given
        String[] args = {"--path",
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs"};

        //when
        LogsHandler handler = new LogsHandler(args);
        Statistics statistics = handler.getStatistics();

        //then
        long uniqueUsersCounterExpected = 2660;
        long totalEntriesExpected = 51462;
        long averageResponseTimeExpected = 659509;
        var mostStatusCodesExpected = Map.of(404, 33876L, 304, 13330L, 200, 4028L);
        var mostRequiredResources =
            Map.of("/downloads/product_1", 30285L, "/downloads/product_2", 21104L, "/downloads/product_3", 73L);
        assertThat(statistics.getUniqueUsersCounter()).isEqualTo(uniqueUsersCounterExpected);
        assertThat(statistics.getTotalEntries()).isEqualTo(totalEntriesExpected);
        assertThat(statistics.getAverageResponseSize()).isEqualTo(averageResponseTimeExpected);
        assertThat(statistics.getFirstKMostStatusCodes(3)).containsAllEntriesOf(mostStatusCodesExpected);
        assertThat(statistics.getFirstKMostRequiredResources(3)).containsAllEntriesOf(mostRequiredResources);
    }

    @Test
    public void testADOCLogPrinter() {
        //given
        String[] args =
            {"--path", sep("./src/test/java/edu/project3/test_logs/logs.txt"),
                sep("./src/test/java/edu/project3/test_logs/logs2.txt"),
                "--format", "ADOC"};

        //when
        LogsHandler handler = new LogsHandler(args);
        LogPrinter printer = handler.getLogPrinter();
        String result = printer.print(0, 3, 3);

        //then
        String expectedResult = """
            ==== Общая информация

            |===
            | Метрика | Значение

            |Файл(-ы)
            |%s, %s

            |Начальная дата
            |-

            |Конечная дата
            |-

            |Количество запросов
            |35

            |Средний размер
            |301

            |Количество уникальных пользователей
            |14
            |===

            ==== Запрашиваемые ресурсы

            |===
            | Ресурс | Количество

            |/downloads/product_1
            |24

            |/downloads/product_2
            |11
            |===

            ==== Коды ответа

            |===
            | Коды | Имя | Количество

            |304
            |Not Modified
            |23

            |404
            |Not Found
            |6

            |200
            |OK
            |6
            |===""".formatted(
            sep("./src/test/java/edu/project3/test_logs/logs.txt"),
            sep("./src/test/java/edu/project3/test_logs/logs2.txt")
        );
            assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testMarkdownLogPrinter() {
        //given
        String[] args =
            {"--path", sep("./src/test/java/edu/project3/test_logs/logs.txt"),
                sep("./src/test/java/edu/project3/test_logs/logs2.txt"),
                "--format", "MARKDOWN"};

        //when
        LogsHandler handler = new LogsHandler(args);
        LogPrinter printer = handler.getLogPrinter();
        String result = printer.print(0, 3, 3);

        //then
        String expectedResult = """
            #### Общая информация

            | Метрика | Значение |
            |:---:|:---:|
            |Файл(-ы)|%s, %s|
            |Начальная дата|-|
            |Конечная дата|-|
            |Количество запросов|35|
            |Средний размер|301|
            |Количество уникальных пользователей|14|

            #### Запрашиваемые ресурсы

            | Ресурс | Количество |
            |:---:|:---:|
            |/downloads/product_1|24|
            |/downloads/product_2|11|

            #### Коды ответа

            | Коды | Имя | Количество |
            |:---:|:---:|:---:|
            |304|Not Modified|23|
            |404|Not Found|6|
            |200|OK|6|
            """.formatted(
            sep("./src/test/java/edu/project3/test_logs/logs.txt"),
            sep("./src/test/java/edu/project3/test_logs/logs2.txt")
        );
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testMostCodesByRemoteUser() {
        //given
        String[] args =
            {"--path", sep("./src/test/java/edu/project3/test_logs/logs3.txt")};

        //when
        LogsHandler handler = new LogsHandler(args);
        Statistics statistics = handler.getStatistics();
        var result = statistics.getMostCodesByRemoteAddress();

        //then
        Map<String, HttpStatus> expectedResult = Map.of(
            "217.212.243.9", new HttpStatus(404),
            "216.46.173.126", new HttpStatus(404),
            "37.16.72.233", new HttpStatus(304)
        );
        assertThat(result).containsAllEntriesOf(expectedResult);
    }

    private static String sep(String path) {
        return path.replaceAll("/", Matcher.quoteReplacement(File.separator));
    }
}
