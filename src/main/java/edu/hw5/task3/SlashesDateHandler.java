package edu.hw5.task3;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlashesDateHandler extends DateHandler {

    private static final Pattern DATE_PATTERN = Pattern.compile("^([1-9]|[12]\\d|3[01])/([1-9]|1[0-2])/(\\d{3,4})$");

    @Override
    public LocalDate handleRequest(String request) {
        Matcher matcher = DATE_PATTERN.matcher(request);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(3));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(1));
            return LocalDate.of(year, month, day);
        } else {
            return next.handleRequest(request);
        }
    }
}
