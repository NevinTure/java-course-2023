package edu.project3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {

    private final List<NginxLogEntry> entries;
    private final LocalDate from;
    private final LocalDate to;
    private final long totalEntries;
    private final Map<String, Long> requiredResourcesCounter;
    private final Map<Integer, Long> statusCodesCounter;
    private final long averageResponseSize;

    public Statistics(List<NginxLogEntry> entries, LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
        this.entries = trimEntries(entries);
        this.totalEntries = calculateTotalEntries();
        this.requiredResourcesCounter = calculateRequiredResources();
        this.statusCodesCounter = calculateStatusCodes();
        this.averageResponseSize = calculateAverageResponseSize();
    }

    private List<NginxLogEntry> trimEntries(List<NginxLogEntry> entries) {
        List<NginxLogEntry> remainEntries = new ArrayList<>(entries.size() / 2);
        for (NginxLogEntry entry : entries) {
            if (from.isBefore(entry.timeLocal().toLocalDate())
                || to.isAfter(entry.timeLocal().toLocalDate())) {
                remainEntries.add(entry);
            }
        }
        return remainEntries;
    }

    private long calculateTotalEntries() {
        return entries.size();
    }

    private Map<String, Long> calculateRequiredResources() {
        return entries
            .stream()
            .collect(Collectors
                .groupingBy(
                    v -> v.request().getUrn(),
                    Collectors.counting()
                )
            );
    }

    private Map<Integer, Long> calculateStatusCodes() {
        return entries
            .stream()
            .collect(Collectors
                .groupingBy(
                    v -> v.status().getExact()
                    , Collectors.counting()
                )
            );
    }

    private long calculateAverageResponseSize() {
        return entries
            .stream()
            .reduce(0L, (v1, v2) -> v1 + v2.bodyBytesSend(), Long::sum) / entries.size();
    }

    public long getTotalEntries() {
        return totalEntries;
    }

    public Map<String, Long> getRequiredResourcesCounter() {
        return requiredResourcesCounter;
    }

    public Map<Integer, Long> getStatusCodesCounter() {
        return statusCodesCounter;
    }

    public long getAverageResponseSize() {
        return averageResponseSize;
    }
}
