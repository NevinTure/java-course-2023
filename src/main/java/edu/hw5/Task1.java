package edu.hw5;

import edu.hw5.util.TimeSpend;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Task1 {

    private Task1() {
    }

    public static TimeSpend averageTimeSpend(String[] durationStrs) {
        long totalMinutes = 0;
        for (String durationStr : durationStrs) {
            String[] splitTime = durationStr.split(" - ");
            LocalDateTime formattedTimeFrom = parseStrToDateTime(splitTime[0]);
            LocalDateTime formattedTimeTo = parseStrToDateTime(splitTime[1]);
            if (formattedTimeFrom.isAfter(formattedTimeTo)) {
                throw new IllegalArgumentException("Illegal date");
            }
            totalMinutes += Duration.between(formattedTimeFrom, formattedTimeTo).toMinutes();
        }
        int averageTotalMinutes = (int) (totalMinutes / durationStrs.length);
        return new TimeSpend(averageTotalMinutes / 60, averageTotalMinutes % 60);
    }

    public static LocalDateTime parseStrToDateTime(String str) {
        String formattedStr = str.replace(", ", "T") + ":00";
        return LocalDateTime.parse(formattedStr);
    }
}
