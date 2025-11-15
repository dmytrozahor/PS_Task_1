package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    static final Path TEST_PATH = Path.of("./data/test/input/test.json");

    @Mock
    private EntityFSProvider fsProvider;

    private StatisticsService statisticsService;

    static final String TEST_SINGLE_JSON =
            """
            [
                  {
                      "title": "Wuthering Heights",
                      "author": "Emily Brontë",
                      "year_published": 1847,
                      "genre": "Gothic, Dark Romance"
                  }
            ]
            """;

    static final String TEST_MULTIPLE_JSON =
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

    @BeforeEach
    void setUp() throws IOException {
        statisticsService = new StatisticsService(fsProvider, StatisticsAttributeType.TITLE);
    }

    @Test
    public void validAnalyzeSingle() throws IOException {
        Mockito.when(fsProvider.getInputStream(eq(TEST_PATH)))
                .thenReturn(new ByteArrayInputStream(TEST_SINGLE_JSON.getBytes()));

        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(TEST_PATH.toString());

        statisticsService.analyze(TEST_PATH);

        final Statistics statistics = statisticsService.getStatistics(bookshelf);

        assertEquals(1, statistics.getOccurrences("Wuthering Heights"));
    }

    @Test
    public void validAnalyzeMultiple() throws IOException {
        Mockito.when(fsProvider.getInputStream(eq(TEST_PATH)))
                .thenReturn(new ByteArrayInputStream(TEST_MULTIPLE_JSON.getBytes()));

        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(TEST_PATH.toString());

        statisticsService.analyze(TEST_PATH);

        final Statistics statistics = statisticsService.getStatistics(bookshelf);

        assertEquals(2, statistics.getOccurrences("Wuthering Heights"));
    }

}
