package edu.project3;

import edu.project3.log_entry.HttpStatus;
import edu.project3.log_entry.NginxLogEntry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics {

    private final List<NginxLogEntry> entries;
    private final List<String> paths;
    private final LocalDate from;
    private final LocalDate to;
    private final long totalEntries;
    private final Map<String, Long> requiredResourcesCounter;
    private final Map<Integer, Long> statusCodesCounter;
    private final long averageResponseSize;
    private final Map<String, HttpStatus> mostCodesByRemoteAddress;
    private final Long uniqueUsersCounter;

    public Statistics(List<String> paths, List<NginxLogEntry> entries, LocalDate from, LocalDate to) {
        this.paths = paths;
        this.from = from;
        this.to = to;
        this.entries = trimEntries(entries);
        this.totalEntries = calculateTotalEntries();
        this.requiredResourcesCounter = calculateRequiredResources();
        this.statusCodesCounter = calculateStatusCodes();
        this.averageResponseSize = calculateAverageResponseSize();
        this.mostCodesByRemoteAddress = calculateMostCodesByRemoteAddress();
        this.uniqueUsersCounter = calculateUniqueUsers();
    }

    private List<NginxLogEntry> trimEntries(List<NginxLogEntry> entries) {
        List<NginxLogEntry> remainEntries = new ArrayList<>(entries.size() / 2);
        for (NginxLogEntry entry : entries) {
            if (!from.isAfter(entry.timeLocal().toLocalDate())
                && !to.isBefore(entry.timeLocal().toLocalDate())) {
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
                    v -> v.status().getExact(),
                    Collectors.counting()
                )
            );
    }

    private long calculateAverageResponseSize() {
        return entries
            .stream()
            .reduce(0L, (v1, v2) -> v1 + v2.bodyBytesSend(), Long::sum) / entries.size();
    }

    private Map<String, HttpStatus> calculateMostCodesByRemoteAddress() {
        return entries
            .stream()
            .collect(Collectors.groupingBy(NginxLogEntry::remoteAddr))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                v -> v
                    .getValue()
                    .stream()
                    .map(NginxLogEntry::status)
                    .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                    ))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .get()
                    .getKey()
            ));
    }

    private long calculateUniqueUsers() {
        return entries
            .stream()
            .collect(Collectors.groupingBy(NginxLogEntry::remoteAddr))
            .entrySet()
            .size();
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

    public Map<String, HttpStatus> getMostCodesByRemoteAddress() {
        return mostCodesByRemoteAddress;
    }

    public Long getUniqueUsersCounter() {
        return uniqueUsersCounter;
    }

    public Map<String, Long> getFirstKMostRequiredResources(int k) {
        return requiredResourcesCounter
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(k)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (v1, v2) -> v1,
                LinkedHashMap::new
            ));
    }

    public Map<Integer, Long> getFirstKMostStatusCodes(int k) {
        return statusCodesCounter
            .entrySet()
            .stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(k)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (v1, v2) -> v1,
                LinkedHashMap::new
            ));
    }

    public List<NginxLogEntry> getEntries() {
        return entries;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public List<String> getPaths() {
        return paths;
    }
}
