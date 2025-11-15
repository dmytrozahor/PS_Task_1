package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(2)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 5, time = 3)
public class FileProcessorPerformanceOverhead {

    @SuppressWarnings("unused")
    @Param({"GENRE"})
    private String attribute;

    private EntityFileProcessor entityFileProcessor;

    @Setup(Level.Trial)
    public void setup() throws IOException {
        StatisticsAttributeType type = StatisticsAttributeType.valueOf(attribute);
        BenchFSProvider fsProvider = new BenchFSProvider();
        fsProvider.readFiles();

        entityFileProcessor = new EntityFileProcessor(fsProvider);

        entityFileProcessor.init(type,
                "./data/benchmark/",
                Runtime.getRuntime().availableProcessors()
        );
    }


    /**
     * Optionally test the performance with a thread overhead.
     */

    @Benchmark
    public void processMediocreFileWithContextSwitch() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        entityFileProcessor.processInputFiles(
                (v) -> {},
                latch
        );

        if (!latch.await(3, TimeUnit.SECONDS)){
            throw new RuntimeException("Failed to wait for mediocre file to finish");
        }
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