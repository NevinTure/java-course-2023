package edu.project3;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class LogsHandler {

    private final List<String> rawPaths;
    private final LocalDate from;
    private final LocalDate to;
    private final Format format;
    private List<NginxLogEntry> logs;
    private Statistics statistics;

    public LogsHandler(String[] args) {
        rawPaths = ArgumentsParser.parsePaths(args);
        from = ArgumentsParser.parseFrom(args);
        to = ArgumentsParser.parseTo(args);
        format = ArgumentsParser.parseFormat(args);
        initLogs();
    }

    private void initLogs() {
        List<String> rawData = FileDataResolver.getAll(rawPaths);
        logs = LogParser.parseAll(rawData);
    }



    public Statistics getStatistics() {
        if (statistics == null) {
            statistics = new Statistics(logs, from, to);
        }
        return statistics;
    }
}
