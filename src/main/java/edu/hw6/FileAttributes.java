package edu.hw6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record FileAttributes(String name, String extension) {

    private static final Pattern FILE_NAME_PATTERN =
        Pattern.compile("^(?<prefix>.*)(?<suffix>\\..*)$");

    public static FileAttributes parse(String s) {
        if (s.contains(".")) {
            Matcher matcher = FILE_NAME_PATTERN.matcher(s);
            matcher.find();
            return new FileAttributes(matcher.group("prefix"), matcher.group("suffix"));
        } else {
            return new FileAttributes(s, "");
        }
    }

    @Override public String toString() {
        return name + extension;
    }
}
