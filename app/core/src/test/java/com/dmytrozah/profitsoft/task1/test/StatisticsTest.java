package com.dmytrozah.profitsoft.task1.test;

import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsTest {

    /**
     * Tests, whether {@link Statistics#merge(Statistics)} is merging statistics correctly
     */

    @Test
    public void correctPopulation(){
        final Statistics first = new Statistics();
        final Statistics second = new Statistics();

        first.increaseOccurrences("test", 1L);
        second.increaseOccurrences("test", 1L);

        first.merge(second);

        assertEquals(2, first.getOccurrences().get("test"));
    }

    /**
     * Tests, whether {@link Statistics#increaseOccurrences(String, long)} is working as intended.
     */

    @Test
    public void occurrencesAccumulation(){
        final Statistics stats = new Statistics();

        stats.increaseOccurrences("test", 1L);

        assertEquals(1L, stats.getOccurrences("test"));
    }
}
