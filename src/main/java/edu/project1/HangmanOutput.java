package edu.project1;

import java.util.ArrayList;
import java.util.List;

public class HangmanOutput {

    private final List<String> output;

    public HangmanOutput() {
        this.output = new ArrayList<>();
    }

    public void addWin() {
        output.set(output.size() - 1, getCurrentState() + "You won!");
    }

    public void addDefeat() {
        output.set(output.size() - 1, getCurrentState() + "You lost!");
    }

    public void addFailedGuess(int maxAttempts, int attempts, String word) {
        output.add(String.format(
            "Missed, mistake %d out of %d.%n%n" +
                "The word: %s%n%n", attempts, maxAttempts, word));
    }

    public void addSuccessfulGuess(String word) {
        output.add(String.format("Hit!%n%n" +
            "The word: %s%n%n", word));
    }

    private String getCurrentState() {
        return output.get(output.size() - 1);
    }

    public String getTotal() {
        return String.join("", output);
    }

    public void clear() {
        output.clear();
    }
}
