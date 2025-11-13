package com.dmytrozah.profitsoft.task1;


import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 3)
public class FileProcessorPerformance {
    @Param({"1", "2", "4", "8", "7"})
    private int threads;

    @Param({"GENRE"})
    private String attribute;

    @Benchmark
    public void processMediocreFile() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StatisticsAttributeType type = StatisticsAttributeType.valueOf(attribute);
        StatisticsService statisticsService = new StatisticsService(type);
        EntityFileProcessor entityFileProcessor = new EntityFileProcessor();

        entityFileProcessor.init(statisticsService, "./data/mediocre", type, threads, latch);
        latch.await();
    }
}
