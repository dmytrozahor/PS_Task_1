package com.dmytrozah.profitsoft.task1.core;

import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.statistics.StatisticsGenerator;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsService {

    private final Map<Bookshelf, Statistics> statistics = new ConcurrentHashMap<>();

    private final StatisticsGenerator generator;

    private final StatisticsAttributeType attributeType;

    private Statistics aggregatedStatistics;

    public StatisticsService(final StatisticsAttributeType attributeType){
        this.generator = new StatisticsGenerator();
        this.attributeType = attributeType;
    }

    public void analyze(Bookshelf bookshelf, Path path) {
        final Statistics generated = generator.generate(this, bookshelf, path, attributeType);

        this.statistics.put(bookshelf, generated);
    }

    public Statistics getAggregatedStatistics() {
        if (aggregatedStatistics == null) {
            aggregatedStatistics = new Statistics();
        }

        statistics.values().forEach((statistics) -> {
            aggregatedStatistics.populate(statistics);
        });

        return aggregatedStatistics;
    }

    public Statistics getStatistics(Bookshelf bookshelf) {
        if (!statistics.containsKey(bookshelf)) {
            statistics.put(bookshelf, new Statistics());
        }

        return statistics.get(bookshelf);
    }

}
