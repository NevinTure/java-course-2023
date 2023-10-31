package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {

    public static boolean subString(String s, String t) {
        Pattern pattern = Pattern.compile(String.format("^.*%s.*$", s));
        Matcher matcher = pattern.matcher(t);
        return matcher.find();
    }
}
