package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public class Main {

    public static Optional<LocalDate> parseDate(String string) {
        DateHandler dashes = new DashesDateHandler();
        DateHandler defined = new DefinedDaysAgoDateHandler();
        DateHandler slashes = new SlashesDateHandler();
        DateHandler today = new TodayDateHandler();
        DateHandler tomorrow = new TomorrowDateHandler();
        DateHandler yesterday = new YesterdayDateHandler();
        DateHandler wrong = new WrongDateHandler();
        dashes.setNext(defined);
        defined.setNext(slashes);
        slashes.setNext(today);
        today.setNext(tomorrow);
        tomorrow.setNext(yesterday);
        yesterday.setNext(wrong);

        LocalDate date = dashes.handleRequest(string);
        if (date == null) {
            return Optional.empty();
        } else {
            return Optional.of(date);
        }
    }
}
