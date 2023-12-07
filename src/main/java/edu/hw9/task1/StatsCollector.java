package edu.hw9.task1;

import java.io.Closeable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsCollector implements Closeable {

    private final Map<String, Double> stats;
    private final ExecutorService service;

    public StatsCollector(int threadsAmount) {
        stats = new ConcurrentHashMap<>();
        service = Executors.newFixedThreadPool(threadsAmount);
    }

    public void push(String metricName, double[] data) {
        service.submit(() ->
            stats.put(metricName + "Average", Arrays.stream(data).average().orElse(0)));
        service.submit(() ->
            stats.put(metricName + "Max", Arrays.stream(data).max().orElse(0)));
        service.submit(() ->
            stats.put(metricName + "Min", Arrays.stream(data).min().orElse(0)));
        service.submit(() ->
            stats.put(metricName + "Sum", Arrays.stream(data).sum()));
    }

    public Map<String, Double> stats() {
        return stats;
    }

    @Override
    public void close() {
        if (service != null) {
            service.close();
        }
    }
}
