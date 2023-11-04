package edu.project3;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class LogsHandler {

    private final List<Path> paths;
    private final LocalDate from;
    private final LocalDate to;
    private final Format format;
    private Statistics statistics;

    public LogsHandler(String[] args) {
        paths = ArgumentsParser.parsePaths(args);
        from = ArgumentsParser.parseFrom(args);
        to = ArgumentsParser.parseTo(args);
        format = ArgumentsParser.parseFormat(args);
    }



    public Statistics getStatistics() {
        if (statistics == null) {
            statistics = new Statistics(null);
        }
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
