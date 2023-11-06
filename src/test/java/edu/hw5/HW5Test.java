package edu.hw5;

import edu.hw5.util.TimeSpend;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static edu.hw5.Task1.averageTimeSpend;
import static edu.hw5.Task2.getAllFriday13thOfYear;
import static edu.hw5.Task2.getNextFriday13th;
import static edu.hw5.Task4.passwordChecker;
import static edu.hw5.Task5.checkAutoNumber;
import static edu.hw5.Task6.subString;
import static edu.hw5.task3.Main.parseDate;
import static edu.hw5.task7_8.Main.ANY_STRING_EXCEPT_2_OR_3_ONES;
import static edu.hw5.task7_8.Main.AT_LEAST_2_ZEROS_AND_LESS_THAT_2_ONES;
import static edu.hw5.task7_8.Main.AT_LEAST_3_SYMBOLS_AND_THIRD_IS_0;
import static edu.hw5.task7_8.Main.ENDS_AND_STARTS_THE_SAME;
import static edu.hw5.task7_8.Main.EVERY_ODD_SYMBOL_IS_1;
import static edu.hw5.task7_8.Main.NOT_CONSECUTIVE_ONES;
import static edu.hw5.task7_8.Main.ODD_SIZE;
import static edu.hw5.task7_8.Main.SIZE_FROM_1_TO_3_SYMBOLS;
import static edu.hw5.task7_8.Main.STARTS_WITH_0_ODD_SIZE_OR_STARTS_WITH_1_EVEN_SIZE;
import static edu.hw5.task7_8.Main.ZERO_MULTIPLE_3;
import static org.assertj.core.api.Assertions.assertThat;

public class HW5Test {

    private static Arguments[] averageTimeSpendParams() {
        return new Arguments[] {
            Arguments.of(
                new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20"},
                new TimeSpend(3, 40)
            ),
            Arguments.of(
                new String[] {"2022-03-12, 20:20 - 2022-03-13, 20:20", "2000-04-01, 10:00 - 2000-04-02, 10:00"},
                new TimeSpend(24, 0)
            ),
            Arguments.of(new String[] {"2003-04-10, 13:30 - 2003-04-10, 13:40",
                "2003-04-11, 08:15 - 2003-04-11, 08:35"}, new TimeSpend(0, 15))
        };
    }

    public static Arguments[] allFridays13thOfYearParams() {
        return new Arguments[] {
            Arguments.of(
                1925,
                List.of(
                    LocalDate.of(1925, 2, 13),
                    LocalDate.of(1925, 3, 13),
                    LocalDate.of(1925, 11, 13))
            ),
            Arguments.of(
                1985,
                List.of(
                    LocalDate.of(1985, 9, 13),
                    LocalDate.of(1985, 12, 13))
            ),
            Arguments.of(
                2010,
                List.of(LocalDate.of(2010, 8, 13)))
        };
    }

    public static Arguments[] nextFriday13thParams() {
        return new Arguments[] {
            Arguments.of(LocalDate.of(2040, 1, 10), LocalDate.of(2040, 1, 13)),
            Arguments.of(LocalDate.of(2001, 8, 1), LocalDate.of(2002, 9, 13)),
            Arguments.of(LocalDate.of(2020, 3, 13), LocalDate.of(2020, 11, 13))
        };
    }

    public static Arguments[] parseDateParams() {
        return new Arguments[] {
            Arguments.of("2001-3-29", Optional.of(LocalDate.of(2001, 3, 29))),
            Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234))),
            Arguments.of("wrong date", Optional.empty())
        };
    }

    public static Arguments[] passwordCheckerParams() {
        return new Arguments[] {
            Arguments.of("password", false),
            Arguments.of("@@", true),
            Arguments.of("pretty_strong_password_with_$_symbol", true)
        };
    }

    public static Arguments[] autoNumberParams() {
        return new Arguments[] {
            Arguments.of("А123ВЕ777", true),
            Arguments.of("О777ОО177", true),
            Arguments.of("А123ВГ77", false),
            Arguments.of("А123ВЕ7777", false)
        };
    }

    public static Arguments[] subStringParams() {
        return new Arguments[] {
            Arguments.of("abc", "achfdbaabgabcaabg", true),
            Arguments.of("string", "substring", true),
            Arguments.of("substring", "string", false),
            Arguments.of("b", "a", false)
        };
    }

    @ParameterizedTest
    @MethodSource("averageTimeSpendParams")
    void testAverageTimeSpend(String[] durationStrs, TimeSpend expectedResult) {
        //when
        TimeSpend timeSpend = averageTimeSpend(durationStrs);

        //then
        assertThat(timeSpend.hours()).isEqualTo(expectedResult.hours());
        assertThat(timeSpend.minutes()).isEqualTo(expectedResult.minutes());
    }

    @ParameterizedTest
    @MethodSource("allFridays13thOfYearParams")
    void testGetAllFriday13thOfYear(int year, List<LocalDate> expectedResult) {
        //when
        List<LocalDate> fridays = getAllFriday13thOfYear(year);

        //then
        assertThat(fridays).containsExactlyElementsOf(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("nextFriday13thParams")
    void testNextFriday13th(LocalDate date, LocalDate expectedResult) {
        //when
        LocalDate friday = getNextFriday13th(date);

        //then
        assertThat(friday).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("parseDateParams")
    void testParseDate(String str, Optional<LocalDate> expectedResult) {
        //when
        Optional<LocalDate> result = parseDate(str);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("passwordCheckerParams")
    void testPasswordChecker(String password, boolean expectedResult) {
        //when
        boolean result = passwordChecker(password);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("autoNumberParams")
    void testCheckAutoNumber(String number, boolean expectedResult) {
        //when
        boolean result = checkAutoNumber(number);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("subStringParams")
    void testSubString(String s, String t, boolean expectedResult) {
        //when
        boolean result = subString(s, t);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testAT_LEAST_3_SYMBOLS_AND_THIRD_IS_0Regex() {
        List<String> trueTests = List.of("000", "110", "1101110", "0001");
        List<String> falseTests = List.of("0", "001", "0010000");

        trueTests.forEach(v ->
            assertThat(AT_LEAST_3_SYMBOLS_AND_THIRD_IS_0.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(AT_LEAST_3_SYMBOLS_AND_THIRD_IS_0.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testENDS_AND_STARTS_THE_SAMERegex() {
        List<String> trueTests = List.of("000", "111", "0101110", "1001");
        List<String> falseTests = List.of("01", "001", "1010000");

        trueTests.forEach(v ->
            assertThat(ENDS_AND_STARTS_THE_SAME.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(ENDS_AND_STARTS_THE_SAME.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testSIZE_FROM_1_TO_3_SYMBOLSRegex() {
        List<String> trueTests = List.of("0", "11", "010");
        List<String> falseTests = List.of("10000", "", "1010");

        trueTests.forEach(v ->
            assertThat(SIZE_FROM_1_TO_3_SYMBOLS.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(SIZE_FROM_1_TO_3_SYMBOLS.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testODD_SIZERegex() {
        List<String> trueTests = List.of("0", "111", "01011111111111111111110");
        List<String> falseTests = List.of("100001", "1000", "10");

        trueTests.forEach(v ->
            assertThat(ODD_SIZE.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(ODD_SIZE.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testSTARTS_WITH_0_ODD_SIZE_OR_STARTS_WITH_1_EVEN_SIZERegex() {
        List<String> trueTests = List.of("000", "11", "10110110");
        List<String> falseTests = List.of("10000", "0000", "1");

        trueTests.forEach(v ->
            assertThat(STARTS_WITH_0_ODD_SIZE_OR_STARTS_WITH_1_EVEN_SIZE.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(STARTS_WITH_0_ODD_SIZE_OR_STARTS_WITH_1_EVEN_SIZE.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testANY_STRING_EXCEPT_2_OR_3_ONESRegex() {
        List<String> trueTests = List.of("000", "110001", "11110110");
        List<String> falseTests = List.of("11", "111");

        trueTests.forEach(v ->
            assertThat(ANY_STRING_EXCEPT_2_OR_3_ONES.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(ANY_STRING_EXCEPT_2_OR_3_ONES.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testEVERY_ODD_SYMBOL_IS_1Regex() {
        List<String> trueTests = List.of("101", "111011", "1", "10");
        List<String> falseTests = List.of("110", "011", "000");

        trueTests.forEach(v ->
            assertThat(EVERY_ODD_SYMBOL_IS_1.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(EVERY_ODD_SYMBOL_IS_1.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testAT_LEAST_2_ZEROS_AND_LESS_THAT_2_ONESRegex() {
        List<String> trueTests = List.of("000", "000100000", "100", "00001");
        List<String> falseTests = List.of("0011", "11000", "011");

        trueTests.forEach(v ->
            assertThat(AT_LEAST_2_ZEROS_AND_LESS_THAT_2_ONES.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(AT_LEAST_2_ZEROS_AND_LESS_THAT_2_ONES.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testNOT_CONSECUTIVE_ONESRegex() {
        List<String> trueTests = List.of("10101010", "00010001", "0", "1");
        List<String> falseTests = List.of("0011", "11000", "0110110");

        trueTests.forEach(v ->
            assertThat(NOT_CONSECUTIVE_ONES.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(NOT_CONSECUTIVE_ONES.matcher(v).find())
                .isFalse()
        );
    }

    @Test
    void testZERO_MULTIPLE_3Regex() {
        List<String> trueTests = List.of("1010101", "00010001", "01100", "000");
        List<String> falseTests = List.of("01011", "110", "01101100");

        trueTests.forEach(v ->
            assertThat(ZERO_MULTIPLE_3.matcher(v).find())
                .isTrue()
        );
        falseTests.forEach(v ->
            assertThat(ZERO_MULTIPLE_3.matcher(v).find())
                .isFalse()
        );
    }
}
