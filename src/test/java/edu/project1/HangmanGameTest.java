package edu.project1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HangmanGameTest {

    private static HangmanGame game;
    private static Dictionary dictionary;


    @BeforeAll
    public static void setUp() {
        StringBuilder output = new StringBuilder();
        dictionary = new Dictionary();
        game = new HangmanGame(dictionary, output);
    }

    @BeforeEach
    public void clearDict() {
        dictionary.clear();
    }

    @Test
    void testIncorrectWordLength() {
        //given
        String incorrectWord = "";

        //when
        dictionary.addWord(incorrectWord);
        game.start();
        String result = game.getCurrentState();

        //then
        assertThat(result).isEqualTo("Dictionary is empty, cannot start a game!\n");
    }

    @Test
    void testLoseBeforeAttemptsLeft() {
        //given
        String word = "hello";
        String input = "a";
        int attempts = 1;

        //when
        dictionary.addWord(word);
        game.setAttempts(attempts);
        game.start();
        game.nextTurn(input);
        String[] result = game.getCurrentState().split("\n");

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
        int attempts = 1;

        //when
        dictionary.addWord(word);
        game.setAttempts(attempts);
        game.start();
        for (int i = 0; i < turns.length; i++) {
            game.nextTurn(turns[i]);
        }
        String[] result = game.getCurrentState().split("\n");

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
        int attempts = 4;

        //when
        dictionary.addWord(word);
        game.setAttempts(attempts);
        game.start();
        for (int i = 0; i < turns.length; i++) {
            game.nextTurn(turns[i]);
        }
        String[] result = game.getCurrentState().split("\n");

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
        int attempts = 1;

        //when
        dictionary.addWord(word);
        game.setAttempts(attempts);
        game.start();
        for (int i = 0; i < turns.length; i++) {
            game.nextTurn(turns[i]);
        }
        String[] result = game.getCurrentState().split("\n");

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
        int attempts = 4;

        //when
        dictionary.addWord(word);
        game.setAttempts(4);
        game.start();
        for (int i = 0; i < turns.length; i++) {
            game.nextTurn(turns[i]);
        }
        String[] result = game.getCurrentState().split("\n");

        //then
        String[] expectedResult = {
            "Guess a letter:", "Missed, mistake 1 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Missed, mistake 2 out of 4.", "", "The word: *****", "",
            "Guess a letter:", "Game is finished.", "Game is not started yet!"};
        assertThat(result).containsExactly(expectedResult);
    }
}
