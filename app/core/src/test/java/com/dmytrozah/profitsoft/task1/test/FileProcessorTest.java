package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileProcessorTest {

    private final StatisticsService statisticsService = new StatisticsService(StatisticsAttributeType.TITLE);
    private final EntityFileProcessor fileProcessor = new EntityFileProcessor();

    @Test
    public void correctAggregatedStatistics() {
        EntityFileProcessor entityFileProcessor = new EntityFileProcessor();

        entityFileProcessor.init(statisticsService, "./data/test/", 1);

        entityFileProcessor.processInputFiles(__ -> {});
        entityFileProcessor.processOutputFiles();

        assertEquals(1, statisticsService.getAggregatedStatistics().getOccurrences("Wuthering Heights"));
    }


    @Test
    public void bookExtraction(){
        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath("./data/test/input/test.json");

        assertEquals(2, fileProcessor.extractBooks(bookshelf, 50).size());
    }

    @Test
    public void threadCount() throws IOException {
        try (final Stream<Path> files = Files.list(Paths.get("./data"))){
            final int fileCount = (int) files.count(); // Very unprobably the file count will exceed Integer.MAX_INT-1

            fileProcessor.init(statisticsService, "./data", fileCount);
            assertEquals(Math.min(Runtime.getRuntime().availableProcessors(), fileCount), fileProcessor.getThreads());
        }
    }

}
