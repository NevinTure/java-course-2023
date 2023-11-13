package edu.project3;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArgumentsParser {

    private ArgumentsParser() {
    }

    public static List<String> parsePaths(String[] args) {
        List<String> pathStrs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--path")) {
                i++;
                while (i < args.length && !args[i].startsWith("--")) {
                    pathStrs.add(args[i]);
                    i++;
                }
            }
        }
        if (pathStrs.isEmpty()) {
            throw new IllegalArgumentException("--path arguments required!");
        }
        return pathStrs
            .stream()
            .flatMap(v -> PathResolver.get(v).stream())
            .toList();
    }

    public static LocalDate parseFrom(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--from")) {
                return LocalDate.parse(args[i + 1]);
            }
        }
        return LocalDate.MIN;
    }

    public static LocalDate parseTo(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--to")) {
                return LocalDate.parse(args[i + 1]);
            }
        }
        return LocalDate.MAX;
    }

    public static Format parseFormat(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--format")) {
                return Format.valueOf(args[i + 1].toUpperCase());
            }
        }
        return Format.MARKDOWN;
    }
}
