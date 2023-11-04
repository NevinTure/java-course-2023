package edu.project3;

import java.util.List;

public class Statistics {

    private List<NginxLogEntry> entries;
    private long totalEntries;
    private String mostRequiredResource;
    private int mostStatusTypes;
    private long averageResponseSize;

    public Statistics(List<NginxLogEntry> entries) {
        this.entries = entries;
    }
}
