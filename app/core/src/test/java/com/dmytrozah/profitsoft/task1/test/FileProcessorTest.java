package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import com.dmytrozah.profitsoft.task1.mapping.exception.MalformedBookshelfException;
import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class FileProcessorTest {

    static final Path TEST_DIR = Path.of("./data/test");
    static final Path TEST_JSON_PATH = TEST_DIR.resolve("/input/test.json");

    static final Path MALFORMED_JSON_PATH = TEST_DIR.resolve("/input/malformed.json");

    static final String MALFORMED_JSON = """
            [
                {
                    "title": "Wuthering Heights",
                    "author": "Emily Brontë",
                    "year_published": 1847,
                    "genre": "Gothic, Dark Romance"
                },
                {
                    "title": "Wuthering Heights 2",
                    "author": "Emily Brontë",
                    "year_published": 1847,
                    "genre": "Gothic, Dark Romance"
                }
            """;

    static final String TEST_JSON =
            """
            [
                  {
                      "title": "Wuthering Heights",
                      "author": "Emily Brontë",
                      "year_published": 1847,
                      "genre": "Gothic, Dark Romance"
                  },
                  {
                      "title": "Wuthering Heights",
                      "author": "Emily Brontë",
                      "year_published": 1847,
                      "genre": "Gothic, Dark Romance"
                  }
            ]
            """;

    @Mock
    private EntityFSProvider fsProvider;

    private EntityFileProcessor fileProcessor;

    @BeforeEach
    public void setup() {
        fileProcessor = new EntityFileProcessor(fsProvider);
    }

    @Test
    public void processingIsCorrect() throws IOException {
        final Path file = Paths.get(TEST_JSON_PATH.toString());

        Mockito.when(fsProvider.listFiles(eq(TEST_DIR.resolve("input"))))
                .thenReturn(Stream.of(file));

        Mockito.when(fsProvider.getInputStream(eq(TEST_JSON_PATH)))
                .thenReturn(new ByteArrayInputStream(TEST_JSON.getBytes()));

        fileProcessor.init(StatisticsAttributeType.TITLE, TEST_DIR.toString(), 1);

        fileProcessor.processInputFiles(__ -> {});
        fileProcessor.processOutputFiles();

        final StatisticsService statisticsService = fileProcessor.getStatisticsService();

        assertNotNull(statisticsService.getAggregatedStatistics());
        assertEquals(2, statisticsService.getAggregatedStatistics().getOccurrences("Wuthering Heights"));
    }


    @Test
    public void bookExtraction() throws IOException {
        Mockito.when(fsProvider.getInputStream(eq(TEST_JSON_PATH)))
                .thenReturn(new ByteArrayInputStream(TEST_JSON.getBytes()));

        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(TEST_JSON_PATH.toString());

        assertEquals(2, fileProcessor.extractBooks(bookshelf, 50).size());
    }

    @Test
    public void malformedBookshelf() throws IOException {
        Mockito.when(fsProvider.getInputStream(eq(MALFORMED_JSON_PATH)))
                .thenReturn(new ByteArrayInputStream(MALFORMED_JSON.getBytes()));

        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(MALFORMED_JSON_PATH.toString());

        assertThrows(MalformedBookshelfException.class, () -> fileProcessor.extractBooks(bookshelf, 50));
    }

    @Test
    public void threadCount() throws IOException {
        try (final Stream<Path> files = Files.list(Paths.get("./data"))){
            final int fileCount = (int) files.count(); // Very unprobably the file count will exceed Integer.MAX_INT-1

            fileProcessor.init(StatisticsAttributeType.TITLE, "./data", fileCount);
            assertEquals(Math.min(Runtime.getRuntime().availableProcessors(), fileCount), fileProcessor.getThreads());
        }
    }

}
