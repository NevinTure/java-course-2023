package edu.hw9.task1;

import java.io.Closeable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatsCollector implements Closeable {

    private final Map<String, Metrics> stats;
    private final ExecutorService service;

    public StatsCollector(int threadsAmount) {
        this.stats = new ConcurrentHashMap<>();
        this.service = Executors.newFixedThreadPool(threadsAmount);
    }

    public void push(String metricName, double[] data) {
        Metrics metrics = stats.computeIfAbsent(metricName, v -> new Metrics(metricName));
        service.submit(() -> {
            metrics.getLock().lock();
            try {
                metrics.setEntriesAmount(metrics.getEntriesAmount() + data.length);
                metrics.setMax(Double.max(Arrays.stream(data).max().orElseThrow(), metrics.getMax()));
                metrics.setMin(Double.min(Arrays.stream(data).min().orElseThrow(), metrics.getMin()));
                metrics.setSum(Arrays.stream(data).sum() + metrics.getSum());
                metrics.setAverage(metrics.getSum() / metrics.getEntriesAmount());
            } finally {
                metrics.getLock().unlock();
            }
        });
    }

    public Map<String, Metrics> stats() {
        return new HashMap<>(stats);
    }

    @Override
    public void close() {
        if (service != null) {
            service.close();
        }
    }
}
