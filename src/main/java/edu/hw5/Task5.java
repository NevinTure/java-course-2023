package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {

    private static final String ALLOWED_LETTERS = "АВЕКМНОРСТУХ";

    public static boolean checkAutoNumber(String number) {
        Pattern pattern = Pattern.compile(String.format("[%s]\\d{3}[%s]{2}[1-9]\\d{2}", ALLOWED_LETTERS, ALLOWED_LETTERS));
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }
}
