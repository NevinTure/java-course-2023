package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {

    private static final int FRIDAY_ORDINAL = 5;
    private static final int DAY_OF_MONTH_TO_SEARCH = 13;

    private Task2() {
    }

    public static List<LocalDate> getAllFriday13thOfYear(int year) {
        List<LocalDate> fridays = new ArrayList<>();
        LocalDate currentDate = LocalDate.of(year, 1, DAY_OF_MONTH_TO_SEARCH);
        while (currentDate.getYear() == year) {
            if (currentDate.getDayOfWeek().getValue() == FRIDAY_ORDINAL) {
                fridays.add(currentDate);
            }
            currentDate = currentDate.plusMonths(1);
        }
        return fridays;
    }

    @SuppressWarnings("ParameterAssignment")
    public static LocalDate getNextFriday13th(LocalDate date) {
        return date.with(TemporalAdjusters.ofDateAdjuster(d -> {
            d = d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            while (d.getDayOfMonth() != DAY_OF_MONTH_TO_SEARCH) {
                d = d.plusWeeks(1);
            }
            return d;
        }));
    }
}
