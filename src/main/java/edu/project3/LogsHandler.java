package edu.project3;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class LogsHandler {

    private final List<String> paths;
    private final LocalDate from;
    private final LocalDate to;
    private final Format format;
    private List<NginxLogEntry> logs;
    private Statistics statistics;
    private LogPrinter printer;

    public LogsHandler(String[] args) {
        paths = ArgumentsParser.parsePaths(args);
        from = ArgumentsParser.parseFrom(args);
        to = ArgumentsParser.parseTo(args);
        format = ArgumentsParser.parseFormat(args);
        printer = getPrinter();
        initLogs();
    }

    private void initLogs() {
        List<String> rawData = FileDataResolver.getAll(paths);
        logs = LogParser.parseAll(rawData);
    }



    public Statistics getStatistics() {
        if (statistics == null) {
            statistics = new Statistics(paths, logs, from, to);
        }
        return statistics;
    }

    private LogPrinter getPrinter() {
        if (format.equals(Format.ADOC)) {
            return new ADOCLogPrinter();
        } else if (format.equals(Format.MARKDOWN)) {
            return new MarkdownLogPrinter();
        } else {
            throw new IllegalArgumentException("Unknown log format");
        }
    }

    public String printLogsStatistic() {
        return printer.print(statistics);
    }
}
