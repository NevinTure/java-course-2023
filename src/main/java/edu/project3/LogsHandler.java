package edu.project3;

import edu.project3.log_entry.Format;
import edu.project3.log_entry.NginxLogEntry;
import edu.project3.log_printer.ADOCLogPrinter;
import edu.project3.log_printer.LogPrinter;
import edu.project3.log_printer.MarkdownLogPrinter;
import edu.project3.log_util.ArgumentsParser;
import edu.project3.log_util.FileDataResolver;
import edu.project3.log_util.LogParser;
import java.time.LocalDate;
import java.util.List;

public class LogsHandler {

    private final List<String> paths;
    private final LocalDate from;
    private final LocalDate to;
    private final Format format;
    private List<NginxLogEntry> logs;
    private Statistics statistics;

    public LogsHandler(String[] args) {
        paths = ArgumentsParser.parsePaths(args);
        from = ArgumentsParser.parseFrom(args);
        to = ArgumentsParser.parseTo(args);
        format = ArgumentsParser.parseFormat(args);
    }

    private void initLogs() {
        List<String> rawData = FileDataResolver.getAll(paths);
        logs = LogParser.parseAll(rawData);
    }



    public Statistics getStatistics() {
        if (statistics == null) {
            initLogs();
            statistics = new Statistics(paths, logs, from, to);
        }
        return statistics;
    }

    private LogPrinter getLogPrinter() {
        if (format.equals(Format.ADOC)) {
            return new ADOCLogPrinter(statistics);
        } else if (format.equals(Format.MARKDOWN)) {
            return new MarkdownLogPrinter(statistics);
        } else {
            throw new IllegalArgumentException("Unknown log format");
        }
    }

    public List<String> getPaths() {
        return paths;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Format getFormat() {
        return format;
    }
}
