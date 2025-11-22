package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Map;
import java.util.Objects;

public record ExportEntry(@JacksonXmlProperty(localName = "value") String value,
                          @JacksonXmlProperty(localName = "count") long count) {
    public ExportEntry(String value, long count) {
        this.value = value;
        this.count = count;
    }

    public static ExportEntry ofMapEntry(final Map.Entry<String, Long> entry) {
        return new ExportEntry(entry.getKey(), entry.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExportEntry that = (ExportEntry) o;
        return count == that.count && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }
}
