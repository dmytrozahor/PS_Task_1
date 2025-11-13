package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Map;

public record ExportEntry(@JacksonXmlProperty(localName = "value") String value,
                          @JacksonXmlProperty(localName = "count") long count) {
    public ExportEntry(String value, long count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public long count() {
        return count;
    }

    public static ExportEntry ofMapEntry(final Map.Entry<String, Long> entry) {
        return new ExportEntry(entry.getKey(), entry.getValue());
    }
}
