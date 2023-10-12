package edu.hw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static edu.hw1.Main.countDigits;
import static edu.hw1.Main.countK;
import static edu.hw1.Main.fixString;
import static edu.hw1.Main.isNestable;
import static edu.hw1.Main.isPalindromeDescendant;
import static edu.hw1.Main.knightBoardCapture;
import static edu.hw1.Main.minutesToSeconds;
import static edu.hw1.Main.rotateLeft;
import static edu.hw1.Main.rotateRight;
import static org.assertj.core.api.Assertions.assertThat;

public class HW1Test {

    @Test
    void testCorrectVideoLength() {
        //given
        String lengthStr = "99:56";

        //when
        long length = minutesToSeconds(lengthStr);

        //then
        assertThat(length).isEqualTo(5996);
    }

    @Test
    void testIncorrectVideoLength() {
        //given
        String lengthStr = "15:90";

        //when
        long length = minutesToSeconds(lengthStr);

        //then
        assertThat(length).isEqualTo(-1);
    }

    @Test
    void testNullVideoLength() {
        //given
        String lengthStr = null;

        //when
        long length = minutesToSeconds(lengthStr);

        //then
        assertThat(length).isEqualTo(-1);
    }

    @Test
    void testCountDigitsInPositiveNum() {
        //given
        int num = 10010;

        //when
        int digitsCounter = countDigits(num);

        //then
        assertThat(digitsCounter).isEqualTo(5);
    }

    @Test
    void testCountDigitsInNegativeNum() {
        //given
        int num = -1234;

        //when
        int digitsCounter = countDigits(num);

        //then
        assertThat(digitsCounter).isEqualTo(4);
    }

    @Test
    void testCountDigitsWhenNumEqualsZero() {
        //given
        int num = 0;

        //when
        int digitsCounter = countDigits(num);

        //then
        assertThat(digitsCounter).isEqualTo(1);
    }

    @Test
    void testIsNestableWhenTrue() {
        //given
        int[] a1 = {1, 2, 3, 4};
        int[] a2 = {0, 6};

        //when
        boolean answer = isNestable(a1, a2);

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void testIsNestableWhenFalse() {
        //given
        int[] a1 = {9, 9, 8};
        int[] a2 = {8, 9};

        //when
        boolean answer = isNestable(a1, a2);

        //then
        assertThat(answer).isFalse();
    }

    @Test
    void testIsNestableWhenNull() {
        //given
        int[] a1 = null;
        int[] a2 = {8,9,1};

        //when
        boolean answer = isNestable(a1, a2);

        //then
        assertThat(answer).isFalse();
    }

    @Test
    void testFixString() {
        //given
        String[] strs = {"123456", "hTsii  s aimex dpus rtni.g", "badce"};

        //when
        String[] answer = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            answer[i] = fixString(strs[i]);
        }

        //then
        String[] correctAnswer = {"214365", "This is a mixed up string.", "abcde"};
        assertThat(answer).containsExactly(correctAnswer);
    }

    @ParameterizedTest
    @ValueSource(ints = {11211230, 1010, 4655, 111, 1001})
    void testIsPalindromeDescendantWhenTrue(int num) {
        //when
        boolean answer = isPalindromeDescendant(num);

        //then
        assertThat(answer).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {14132231, 193, 1093, 8882})
    void testIsPalindromeDescendantWhenFalse(int num) {
        //when
        boolean answer = isPalindromeDescendant(num);

        //then
        assertThat(answer).isFalse();
    }

    @Test
    void testCountK() {
        //given
        int[] nums = {3524, 6621, 6554, 1234};

        //when
        int[] answer = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            answer[i] = countK(nums[i]);
        }

        //then
        int[] correctAnswer = {3, 5, 4, 3};
        assertThat(answer).containsExactly(correctAnswer);
    }

    @Test
    void testRotateRight() {
        //given
        int num = 5643;
        int shift = 4;

        //when
        int answer = rotateRight(num, shift);
        //1011000001011 (5643) -> 1011101100000 (5984)

        //then
        assertThat(answer).isEqualTo(5984);
    }

    @Test
    void testRotateLeft() {
        //given
        int num = 5643;
        int shift = 10;

        //when
        int answer = rotateLeft(num, shift);
        //1011000001011 (5643) -> 0111011000001 (3777)

        //then
        assertThat(answer).isEqualTo(3777);
    }

    @Test
    void testKnightBoardCaptureWhenTrue() {
        //given
        int[][] board =
            {
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 0}
            };

        //when
        boolean answer = knightBoardCapture(board);

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void testKnightBoardCaptureWhenFalse() {
        //given
        int[][] board =
            {
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0}
            };

        //when
        boolean answer = knightBoardCapture(board);

        //then
        assertThat(answer).isFalse();
    }
}
