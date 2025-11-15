package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Statistics {
    private final Map<String, Long> occurrences = new ConcurrentHashMap<>();

    public Statistics increaseOccurrences(final String attribute, long value) {
        occurrences.put(attribute, occurrences.getOrDefault(attribute, 0L) + value);

        return this;
    }

    public void merge(Statistics statistics) {
        statistics.getOccurrences().forEach((key, value) -> this.getOccurrences().merge(key, value, Long::sum));
    }

    public long getOccurrences(final String attribute) {
        return occurrences.getOrDefault(attribute, 0L);
    }

    public Map<String, Long> getOccurrences() {
        return occurrences;
    }
}
