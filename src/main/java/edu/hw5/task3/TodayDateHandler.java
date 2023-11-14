package edu.hw5.task3;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodayDateHandler extends DateHandler {

    private static final Pattern DATE_PATTERN = Pattern.compile("today", Pattern.CASE_INSENSITIVE);

    @Override
    public LocalDate handleRequest(String request) {
        Matcher matcher = DATE_PATTERN.matcher(request);
        if (matcher.find()) {
            return LocalDate.now();
        } else {
            return next.handleRequest(request);
        }
    }
}
