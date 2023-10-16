package edu.hw3;

import edu.hw3.contacts_problem.Contact;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw3.contacts_problem.Main.parseContacts;
import static edu.hw3.task_1_to_4.Main.atbash;
import static edu.hw3.task_1_to_4.Main.clusterize;
import static edu.hw3.task_1_to_4.Main.convertToRoman;
import static edu.hw3.task_1_to_4.Main.freqDict;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class HW3Test {

    private static Arguments[] freqDictTestParams() {
        return new Arguments[] {
            Arguments.of(
                new String[] {"a", "bb", "a", "bb"},
                Map.of("bb", 2, "a", 2)
            ),
            Arguments.of(
                new Integer[] {1, 1, 2, 2},
                Map.of(1, 2, 2, 2)
            ),
            Arguments.of(
                new Boolean[] {true, true, true, false},
                Map.of(true, 3, false, 1)
            ),
            Arguments.of(
                new String[] {"код", "код", "код", "bug"},
                Map.of("код", 3, "bug", 1)
            )
        };
    }

    private static Arguments[] convertToRomanParams() {
        return new Arguments[] {
            Arguments.of(
                2, "II"
            ),
            Arguments.of(
                12, "XII"
            ),
            Arguments.of(
                16, "XVI"
            ),
            Arguments.of(
                3999, "MMMCMXCIX"
            )
        };
    }

    private static Arguments[] parseContactsAscParams() {
        return new Arguments[] {
            Arguments.of(new String[] {
                "John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"
            }, List.of(
                new Contact("Thomas", "Aquinas"),
                new Contact("Rene", "Descartes"),
                new Contact("David", "Hume"),
                new Contact("John", "Locke")
            )),
            Arguments.of(new String[] {
                "A B", "B A", "C A", "B"
            }, List.of(
                new Contact("B", "A"),
                new Contact("C", "A"),
                new Contact("A", "B"),
                new Contact("B", null)
            ))
        };
    }

    private static Arguments[] parseContactsDescParams() {
        return new Arguments[] {
            Arguments.of(new String[] {
                "Paul Erdos", "Leonhard Euler", "Carl Gauss"
            }, List.of(
                new Contact("Carl", "Gauss"),
                new Contact("Leonhard", "Euler"),
                new Contact("Paul", "Erdos")
            )),
            Arguments.of(new String[0], List.of()),
            Arguments.of(null, List.of())
        };
    }

    @Test
    void testAtbash() {
        //given
        String str = "Hello world!";

        //when
        String result = atbash(str);

        //then
        String expectedResult = "Svool dliow!";
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testClusterizeSuccessful() {
        //given
        String str = "((()))(())()()(()())";

        //when
        List<String> result = clusterize(str);

        //then
        List<String> expectedResult = List.of("((()))", "(())", "()", "()", "(()())");
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {")", "((", "(()))", "("})
    void testClusterizeFailed(String str) {
        //then
        assertThatIllegalStateException().isThrownBy(() -> clusterize(str));
    }

    @ParameterizedTest
    @MethodSource("freqDictTestParams")
    <T> void testFreqDictWithStrings(T[] a, Map<T, Integer> expectedResult) {
        //when
        Map<T, Integer> result = freqDict(a);

        //then
        assertThat(result).containsAllEntriesOf(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("convertToRomanParams")
    void testConvertToRoman(int num, String expectedResult) {
        //when
        String result = convertToRoman(num);

        //then
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("parseContactsAscParams")
    void testParseContactsAsc(String[] contactStrs, List<Contact> expectedResult) {
        //given
        String sortOrder = "ASC";

        //when
        List<Contact> result = parseContacts(contactStrs, sortOrder);

        //then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("parseContactsDescParams")
    void testParseContactsDesc(String[] contactStrs, List<Contact> expectedResult) {
        //given
        String sortOrder = "DESC";

        //when
        List<Contact> result = parseContacts(contactStrs, sortOrder);

        //then
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Test
    void testParseContactsWithInvalidSortOrder() {
        //given
        String[] contactStrs = {"One", "Two"};
        String sortOrder = "Totally Invalid";

        //then
        assertThatIllegalArgumentException()
            .isThrownBy(() -> parseContacts(contactStrs, sortOrder));
    }
}
