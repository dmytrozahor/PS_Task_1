package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileProcessorTest {

    private final StatisticsService statisticsService = new StatisticsService(StatisticsAttributeType.TITLE);
    private final EntityFileProcessor fileProcessor = new EntityFileProcessor();

    @Test
    public void threadCount() throws IOException {
        try (final Stream<Path> files = Files.list(Paths.get("./data"))){
            final int fileCount = (int) files.count(); // Very unprobably the file count will exceed Integer.MAX_INT-1

            fileProcessor.init(statisticsService, "./data", StatisticsAttributeType.TITLE, fileCount);
            assertEquals(Math.min(Runtime.getRuntime().availableProcessors(), fileCount), fileProcessor.getThreads());
        }
    }

}
