package edu.project3;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Statistics {

    private List<NginxLogEntry> entries;
    private LocalDate from;
    private LocalDate to;
    private long totalEntries;
    private Map<String, Long> mostRequiredResources;
    private int mostStatusTypes;
    private long averageResponseSize;

    public Statistics(List<NginxLogEntry> entries, LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
        this.entries = trimEntries(entries);
        calculateTotalEntries();
        calculateMostRequiredResources();
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

    private void calculateTotalEntries() {
        totalEntries = entries.size();
    }

    private void calculateMostRequiredResources() {
        mostRequiredResources = entries
            .stream()
            .collect(Collectors
                .groupingBy(
                    v -> v.request().getUrn(),
                    Collectors.counting()
                )
            );
    }
}
