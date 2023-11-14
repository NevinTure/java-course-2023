package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {

    public static boolean subString(String s, String t) {
        StringBuilder regex = new StringBuilder(".*");
        for (int i = 0; i < s.length(); i++) {
            regex.append(s.charAt(i)).append(".*");
        }
        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(t);
        return matcher.find();
    }

    private Task6() {
    }
}
