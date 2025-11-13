package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
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

    private final StatisticsService statisticsService = new StatisticsService(StatisticsAttributeType.TITLE);
    private final StatisticsGenerator statisticsGenerator = new StatisticsGenerator();

    static final Path BOOKSHELF_PATH = Paths.get("./data/mediocre/input/mediocre_bookshelf.json");
    static final Bookshelf MEDIOCRE_BOOKSHELF = new Bookshelf(BOOKSHELF_PATH.toString());

    @Benchmark
    public void mediocreTitle(){
        statisticsGenerator.generate(statisticsService, MEDIOCRE_BOOKSHELF, BOOKSHELF_PATH, StatisticsAttributeType.TITLE);
    }

    @Benchmark
    public void mediocreGenres(){
        statisticsGenerator.generate(statisticsService, MEDIOCRE_BOOKSHELF, BOOKSHELF_PATH, StatisticsAttributeType.GENRE);
    }

}
