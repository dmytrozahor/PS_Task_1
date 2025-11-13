package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import java.util.Arrays;
import java.util.Optional;

public enum StatisticsAttributeType {
    TITLE("title", false),

    GENRE("genre", true),

    AUTHOR("author", false),

    YEAR_PUBLISHED("year_published", false);

    private final String key;

    /**
     * Whether the field is comma separated
     */

    private final boolean cs;

    StatisticsAttributeType(String key, boolean cs) {
        this.key = key;
        this.cs = cs;
    }

    public String getKey() {
        return key;
    }

    public static Optional<StatisticsAttributeType> getByKey(String key) {
        return Arrays.stream(StatisticsAttributeType.values())
                .filter(t -> t.getKey().equals(key))
                .findFirst();
    }

    public boolean isCommaSeparated() {
        return cs;
    }
}
