package edu.project3.log_util;

import edu.project3.log_entry.Format;
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
                int j = i + 1;
                while (j < args.length && !args[j].startsWith("--")) {
                    pathStrs.add(args[j]);
                    j++;
                }
                break;
            }
        }
        if (pathStrs.isEmpty()) {
            throw new IllegalArgumentException("--path arguments required!");
        }
        return PathResolver.getAll(pathStrs);
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
