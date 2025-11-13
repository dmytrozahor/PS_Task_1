package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

public class NaiveFileProcessorPerformance {

    public static void main(String[] args) throws InterruptedException {
        int threads = 7;
        int files = 7;

        CountDownLatch latch = new CountDownLatch(1);
        StatisticsAttributeType type = StatisticsAttributeType.valueOf("GENRE");
        StatisticsService statisticsService = new StatisticsService(type);
        EntityFileProcessor processor = new EntityFileProcessor();

        processor.init(statisticsService, "./data/mediocre/", type, threads, latch);

        for (int i = 0; i < 5; i++) {
                processor.processInputFiles(Path.of("./data/mediocre/input/"), false, (v) -> {}, new CountDownLatch(files)); // Warming up
        }

        final long start = System.nanoTime();

        processor.init(statisticsService, "./data/mediocre/", type, threads, latch);

        latch.await();

        double elapsedSec = (System.nanoTime() - start) / 1_000_000_000.0;
        double throughput = 7 / elapsedSec;

        System.out.println(throughput + "file / sec");
    }

}
