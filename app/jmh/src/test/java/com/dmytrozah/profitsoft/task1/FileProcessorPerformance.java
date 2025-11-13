package com.dmytrozah.profitsoft.task1;


import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 3)
public class FileProcessorPerformance {
    @SuppressWarnings("unused")
    @Param({"1", "2", "4", "8", "7", "16"})
    private int threads;

    @SuppressWarnings("unused")
    @Param({"GENRE"})
    private String attribute;

    private EntityFileProcessor entityFileProcessor;

    @Setup(Level.Trial)
    public void setup() {
        StatisticsAttributeType type = StatisticsAttributeType.valueOf(attribute);
        StatisticsService statisticsService = new StatisticsService(type);
        entityFileProcessor = new EntityFileProcessor();

        entityFileProcessor.init(statisticsService,
                "./data/mediocre/",
                threads
        );
    }

    @Benchmark
    public void processMediocreFile() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        entityFileProcessor.processInputFiles(
                (v) -> {},
                latch
        );

        latch.await();
    }

    @TearDown(Level.Trial)
    public void shutdownProcessor(){
        try {
            entityFileProcessor.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
