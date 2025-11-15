package com.dmytrozah.profitsoft.task1.core.entities.statistics;

import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonRootName("statistics")
public record StatisticsExport(@JacksonXmlProperty(localName = "item")
                               @JacksonXmlElementWrapper(useWrapping = false)
                               List<ExportEntry> statistics) {
    public StatisticsExport(List<ExportEntry> statistics) {
        this.statistics = statistics;
    }

    public static StatisticsExport ofEntryList(ArrayList<Map.Entry<String, Long>> statistics) {
        final List<ExportEntry> entries = statistics
                .stream()
                .map(ExportEntry::ofMapEntry)
                .toList();

        return new StatisticsExport(entries);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsExport export = (StatisticsExport) o;
        return Objects.equals(statistics, export.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statistics);
    }
}
