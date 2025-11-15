package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.reader.DefaultFSProvider;
import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;
import com.dmytrozah.profitsoft.task1.mapping.statistics.StatisticsGenerator;
import org.openjdk.jmh.annotations.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000) // Optionally increase to 5 iterations
public class GeneratorPerformance {

    private final EntityFSProvider provider = new DefaultFSProvider();
    private final StatisticsGenerator statisticsGenerator = new StatisticsGenerator();

    static final Path BOOKSHELF_PATH = Paths.get("./data/mediocre/input/mediocre_bookshelve_1.json");

    @Benchmark
    public void mediocreTitle(){
        StatisticsService statisticsService = new StatisticsService(provider, StatisticsAttributeType.TITLE);

        statisticsGenerator.generate(provider, statisticsService, BOOKSHELF_PATH);
    }

    @Benchmark
    public void mediocreGenres(){
        StatisticsService statisticsService = new StatisticsService(provider, StatisticsAttributeType.GENRE);

        statisticsGenerator.generate(provider, statisticsService, BOOKSHELF_PATH);
    }

}
