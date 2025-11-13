package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import java.util.Arrays;
import java.util.Optional;

public enum StatisticsAttributeType {

    TITLE("title"),

    GENRE("genre"),

    AUTHOR("author"),

    YEAR_PUBLISHED("year_published");

    private final String key;

    StatisticsAttributeType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Optional<StatisticsAttributeType> getByKey(String key) {
        return Arrays.stream(StatisticsAttributeType.values())
                .filter(t -> t.getKey().equals(key))
                .findFirst();
    }
}
