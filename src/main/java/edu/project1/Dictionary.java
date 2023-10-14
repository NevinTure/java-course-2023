package edu.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionary {
    private final List<String> storage;
    private final Random generator;

    public Dictionary() {
        storage = new ArrayList<>();
        generator = new Random();
    }

    //Возвращает true, если слово добавилось в словарь
    @SuppressWarnings("ParameterAssignment")
    public boolean addWord(String word) {
        if (word == null
            || word.isEmpty()
            || !word.matches("[a-zA-Z]+")) {
            return false;
        }
        word = word.trim().toLowerCase();
        storage.add(word);
        return true;
    }

    public String getRandomWord() {
        return storage.get(generator.nextInt(storage.size()));
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public void clear() {
        storage.clear();
    }
}
