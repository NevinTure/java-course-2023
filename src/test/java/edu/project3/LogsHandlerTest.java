package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import edu.project3.log_entry.Format;
import edu.project3.log_entry.HttpMethod;
import edu.project3.log_entry.HttpStatus;
import edu.project3.log_entry.NginxLogEntry;
import edu.project3.log_entry.Request;
import edu.project3.log_util.ArgumentsParser;
import edu.project3.log_util.LogParser;
import edu.project3.log_util.PathResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LogsHandlerTest {

    private static Stream<Arguments> wrongArgs() {
        return Stream.of(
            Arguments.of(new String[] {"--path", "/*.txt", "--from", "2010-10-11", "--to", "2012-01-01", "--format",
                "wrong format"}, IllegalArgumentException.class),
            Arguments.of(new String[] {"--path"}, RuntimeException.class),
            Arguments.of(new String[] {"--path", "/*.txt", "--from", "wrong format"}, DateTimeParseException.class)
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

    private final static String TEST_FOLDER = "src/test/java/edu/project3/test_logs";

    @Test
    public void testArgumentParser() {
        //given
        Path testFolder = Path.of(TEST_FOLDER + "\\args_parser").toAbsolutePath();
        Format format;
        LocalDate from;
        LocalDate to;
        List<String> paths;
        String[] args =
            {"--path", testFolder + "\\*", "--from", "2010-10-11", "--to", "2012-01-01", "--format", "adoc"};

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
        String rawPath = "src/test/**/test_logs/*";

        //when
        List<String> paths = PathResolver.get(rawPath);

        //then
        List<String> expectedResult = List.of(
            "src\\test\\java\\edu\\project3\\test_logs\\logs.txt",
            "src\\test\\java\\edu\\project3\\test_logs\\logs2.txt"
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
}
