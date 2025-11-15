package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.statistics.StatisticsGenerator;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 5, timeUnit = TimeUnit.MILLISECONDS, time = 5000) // Optionally increase to 5 iterations
public class GeneratorPerformance {

    private BatchFSProvider provider;
    private StatisticsGenerator statisticsGenerator;

    static final Path BOOKSHELF_PATH = Paths.get("./data/mediocre/input/mediocre_bookshelve_1.json");

    @Setup(Level.Trial)
    public void loadFiles() throws IOException {
        this.provider = new BatchFSProvider();
        this.provider.readFiles();

        this.statisticsGenerator = new StatisticsGenerator();
    }

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
