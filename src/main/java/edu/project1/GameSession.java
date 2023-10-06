package edu.project1;

public class GameSession {

    private final StringBuilder output;
    private final Dictionary dictionary;
    private int attempts;
    private final HangmanGame game;

    //Кол-во попыток по умолчанию == 5
    @SuppressWarnings("MagicNumber")
    public GameSession() {
        this.output = new StringBuilder();
        this.dictionary = new Dictionary();
        this.attempts = 5;
        this.game = new HangmanGame(dictionary, output, attempts);
    }

    public void start() {
        game.start();
    }

    public void nextTurn(String turn) {
        game.nextTurn(turn);
    }

    public String getCurrentState() {
        return output.toString();
    }

    public void addWordToDict(String word) {
        dictionary.addWord(word);
    }

    public void clearDict() {
        dictionary.clear();
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
        game.setAttempts(attempts);
    }

    public int getAttempts() {
        return attempts;
    }
}
