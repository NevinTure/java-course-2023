package edu.project1;

import java.util.Arrays;

public class HangmanGame {
    private final Dictionary dictionary;
    private String wordToGuess;
    private char[] wordChars;
    private int attempts;
    private int currentAttempts;
    private boolean isStarted;
    private final StringBuilder output;

    public HangmanGame(Dictionary dictionary, StringBuilder output, int attempts) {
        this.dictionary = dictionary;
        this.attempts = attempts;
        isStarted = false;
        this.output = output;
    }

    public void start() {
        if (isStarted) {
            output.append("Game already started!\n");
        } else {
            output.setLength(0);
            if (dictionary.isEmpty()) {
                output.append("Dictionary is empty, cannot start a game!\n");
                return;
            }
            initializeGameData();
            currentAttempts = 0;
            isStarted = true;
        }
    }

    @SuppressWarnings("ReturnCount")
    public void nextTurn(String turn) {
        if (!isStarted) {
            output.append("Game is not started yet!\n");
            return;
        }
        output.append("Guess a letter:\n");
        //Возможность сдаться не дожидаясь конца игры
        if (turn.equals("exit")) {
            isStarted = false;
            output.append("Game is finished.\n");
            return;
        }
        if (isValid(turn)) {
            char letter = Character.toLowerCase(turn.charAt(0));
            if (checkLetter(letter)) {
                openLetter(letter);
                output.append("Hit!\n\n");
            } else {
                currentAttempts++;
                output.append("Missed, mistake ")
                    .append(currentAttempts)
                    .append(" out of ")
                    .append(attempts)
                    .append(".\n\n");
            }
            output.append("The word: ")
                .append(printWord())
                .append("\n\n");
            isFinished();
        }
    }

    private void initializeGameData() {
        wordToGuess = dictionary.getRandomWord();
        wordChars = new char[wordToGuess.length()];
        Arrays.fill(wordChars, '*');
    }

    private void isFinished() {
        if (currentAttempts == attempts) {
            isStarted = false;
            output.append("You lose!\n");
        } else {
            for (char chr : wordChars) {
                if (chr == '*') {
                    return;
                }
            }
            isStarted = false;
            output.append("You won!\n");
        }
    }

    private boolean isValid(String data) {
        return data.length() == 1 && Character.isLetter(data.charAt(0));
    }

    private boolean checkLetter(char letter) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    private void openLetter(char letter) {
        for (int i = 0; i < wordChars.length; i++) {
            if (wordToGuess.charAt(i) == letter) {
                wordChars[i] = letter;
            }
        }
    }

    private String printWord() {
        return new String(wordChars);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getCurrentState() {
        return output.toString();
    }
}
