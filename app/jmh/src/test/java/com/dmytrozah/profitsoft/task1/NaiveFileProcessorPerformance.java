package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.util.concurrent.CountDownLatch;

public class NaiveFileProcessorPerformance {

    public static void main(String[] args) throws Exception {
        int[] threadCounts = {1, 2, 4, 8, 5, 16, Runtime.getRuntime().availableProcessors()};

        System.out.println("Only input");

        // Input only
        for (int threadCount : threadCounts) {
            StatisticsAttributeType type = StatisticsAttributeType.valueOf("GENRE");
            BatchFSProvider provider = new BatchFSProvider();
            provider.readFiles();

            EntityFileProcessor processor = new EntityFileProcessor(provider);

            processor.init(type,
                    "./data/benchmark/",
                    threadCount
            );

            processor.init(type,
                    "./data/benchmark/",
                    threadCount
            );

            processor.init(type,
                    "./data/benchmark/",
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

            provider = new BatchFSProvider();
            provider.readFiles();

            processor = new EntityFileProcessor(provider);
            processor.init(type,
                    "./data/benchmark/",
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

        /*
        It seems we shouldn't benchmark the IO with JMH
        However...
        System.out.println("Input + Output");

        // Output
        for (int threadCount : threadCounts) {
            StatisticsAttributeType type = StatisticsAttributeType.valueOf("GENRE");
            EntityFileProcessor processor = new EntityFileProcessor();

            processor.init(type,
                    "./data/mediocre/",
                    threadCount
            );

            processor.init(type,
                    "./data/mediocre/",
                    threadCount
            );

            processor.init(type,
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
            processor.init(type,
                    "./data/mediocre/",
                    threadCount
            );

            final long start = System.nanoTime();
            CountDownLatch latch = new CountDownLatch(1);

            processor.processInputFiles(
                    (v) -> {},
                    latch
            );
            processor.processOutputFiles();

            latch.await();

            double elapsedTime = (System.nanoTime() - start) / 10e6;

            System.out.println("For " + threadCount + " the execution time was: " + elapsedTime + " ms");
        }*/

    }

}
