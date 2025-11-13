package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsServiceTest {

    static final Path TEST_PATH = Paths.get("./data/test/input/test.json");

    private final StatisticsService statisticsService = new StatisticsService(StatisticsAttributeType.TITLE);

    @Test
    public void validAnalyze(){
        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(TEST_PATH.toString());

        statisticsService.analyze(bookshelf, TEST_PATH);

        assertEquals(1, statisticsService.getStatistics(bookshelf).getOccurrences("Wuthering Heights"));
    }

}
