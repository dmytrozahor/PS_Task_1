package com.dmytrozah.profitsoft.task1.core;

import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;
import com.dmytrozah.profitsoft.task1.mapping.statistics.StatisticsGenerator;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsService {
    private final Map<Path, Statistics> statistics = new ConcurrentHashMap<>();

    private final StatisticsGenerator generator;

    private final StatisticsAttributeType attributeType;

    private final EntityFSProvider fsProvider;

    private Statistics aggregatedStatistics;

    public StatisticsService(EntityFSProvider fsProvider, final StatisticsAttributeType attributeType){
        this.fsProvider = fsProvider;

        this.generator = new StatisticsGenerator();
        this.attributeType = attributeType;
    }

    public void analyze(Path path) {
        final Statistics generated = generator.generate(fsProvider, this, path);

        this.statistics.put(path, generated);
    }

    public void analyze(Bookshelf bookshelf){
        analyze(Path.of(bookshelf.getPath()));
    }

    public Statistics getAggregatedStatistics() {
        if (aggregatedStatistics == null) {
            aggregatedStatistics = new Statistics();

            statistics.values().forEach((statistics) -> {
                aggregatedStatistics.merge(statistics);
            });
        }

        return aggregatedStatistics;
    }

    public Statistics getStatistics(Path path) {
        if (!statistics.containsKey(path)) {
            statistics.put(path, new Statistics());
        }

        return statistics.get(path);
    }

    public Statistics getStatistics(Bookshelf bookshelf) {
        return getStatistics(Path.of(bookshelf.getPath()));
    }

    public StatisticsAttributeType getAttributeType() {
        return attributeType;
    }
}
