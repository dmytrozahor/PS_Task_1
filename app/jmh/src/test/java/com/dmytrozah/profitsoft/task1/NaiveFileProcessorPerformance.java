package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.util.concurrent.CountDownLatch;

public class NaiveFileProcessorPerformance {

    public static void main(String[] args) throws Exception {
        int[] threadCounts = {1, 2, 4, 8, 7, 16, Runtime.getRuntime().availableProcessors() + 1};

        for (int threadCount : threadCounts) {
           StatisticsAttributeType type = StatisticsAttributeType.valueOf("GENRE");
            StatisticsService statisticsService = new StatisticsService(type);
            EntityFileProcessor processor = new EntityFileProcessor();

            processor.init(statisticsService,
                    "./data/mediocre/",
                    threadCount
            );

            processor.init(statisticsService,
                    "./data/mediocre/",
                    threadCount
            );

            processor.init(statisticsService,
                    "./data/mediocre/",
                    threadCount
            );

            // Some kind of warm-up task
            for (int i = 0; i < 50; i++) {
                CountDownLatch latch = new CountDownLatch(1);

                processor.processInputFiles(
                        (v) -> {},
                        latch
                );

                latch.await();
            }

            processor = new EntityFileProcessor();
            processor.init(statisticsService,
                    "./data/mediocre/",
                    threadCount
            );

            final long start = System.nanoTime();
            CountDownLatch latch = new CountDownLatch(1);

            processor.processInputFiles(
                    (v) -> {},
                    latch
            );

            latch.await();

            double elapsedTime = (System.nanoTime() - start) / 10e6;

            System.out.println("For " + threadCount + " the execution time was: " + elapsedTime + " ms");

            processor.shutdown();
        }
    }

}
