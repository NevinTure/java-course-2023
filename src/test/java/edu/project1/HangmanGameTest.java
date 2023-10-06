package edu.project1;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HangmanGameTest {

    private static GameSession session;

    @BeforeAll
    public static void setUp() {
        session = new GameSession();
    }

    @BeforeEach
    public void clearDict() {
        session.clearDict();
    }

    @Test
    void testIncorrectWordLength() {
        //given
        String incorrectWord = "";

        //when
        session.addWordToDict(incorrectWord);
        session.start();
        String result = session.getCurrentState();

        //then
        assertThat(result).isEqualTo("Dictionary is empty, cannot start a game!\n");
    }

    @Test
    void testLoseBeforeAttemptsLeft() {
        //given
        String word = "hello";
        String input = "a";

        //when
        session.addWordToDict(word);
        session.setAttempts(1);
        session.start();
        session.nextTurn(input);
        String[] result = session.getCurrentState().split("\n");

        //then
        String[] expectedResult =
            {"Guess a letter:", "Missed, mistake 1 out of 1.", "", "The word: *****", "", "You lose!"};
        assertThat(result).containsExactly(expectedResult);
    }

    @Test
    void testCorrectStateChangeWhenWin() {
        //given
        String word = "hello";
        String[] turns = {"h", "e", "l", "O"};

        //when
        session.addWordToDict(word);
        session.setAttempts(1);
        session.start();
        for (int i = 0; i < turns.length; i++) {
            session.nextTurn(turns[i]);
        }
        String[] result = session.getCurrentState().split("\n");

        //then
        String[] expectedResult = {"Guess a letter:", "Hit!", "", "The word: h****", "",
            "Guess a letter:", "Hit!", "", "The word: he***", "",
            "Guess a letter:", "Hit!", "", "The word: hell*", "",
            "Guess a letter:", "Hit!", "", "The word: hello", "",
            "You won!"};
        assertThat(result).containsExactly(expectedResult);
    }

    @Test
    void testCorrectStateChangeWhenLose() {
        //given
        String word = "hello";
        String[] turns = {"a", "b", "c", "h", "d", "t"};

        //when
        session.addWordToDict(word);
        session.setAttempts(4);
        session.start();
        for (int i = 0; i < turns.length; i++) {
            session.nextTurn(turns[i]);
        }
        String[] result = session.getCurrentState().split("\n");

        //then
        String[] expectedResult = {
            "Guess a letter:", "Missed, mistake 1 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Missed, mistake 2 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Missed, mistake 3 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Hit!", "", "The word: h****", "",
            "Guess a letter:", "Missed, mistake 4 out of 4.", "", "The word: h****", "",
            "You lose!", "Game is not started yet!"};
        assertThat(result).containsExactly(expectedResult);
    }

    @Test
    void testTypos() {
        //given
        String word = "a";
        String[] turns = {"aa", "exittt", "", "right letter", "a"};

        //when
        session.addWordToDict(word);
        session.setAttempts(1);
        session.start();
        for (int i = 0; i < turns.length; i++) {
            session.nextTurn(turns[i]);
        }
        String[] result = session.getCurrentState().split("\n");

        //then
        String[] expectedResult = {"Guess a letter:",
            "Guess a letter:",
            "Guess a letter:",
            "Guess a letter:",
            "Guess a letter:",
            "Hit!", "", "The word: a", "", "You won!"};
        assertThat(result).containsExactly(expectedResult);
    }

    @Test
    void testEarlyGameEnd() {
        //given
        String word = "hello";
        //                  stop word \/
        String[] turns = {"a", "b", "exit", "t"};

        //when
        session.addWordToDict(word);
        session.setAttempts(4);
        session.start();
        for (int i = 0; i < turns.length; i++) {
            session.nextTurn(turns[i]);
        }
        String[] result = session.getCurrentState().split("\n");

        //then
        String[] expectedResult = {
            "Guess a letter:", "Missed, mistake 1 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Missed, mistake 2 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Game is finished.", "Game is not started yet!"};
        assertThat(result).containsExactly(expectedResult);
    }
}
