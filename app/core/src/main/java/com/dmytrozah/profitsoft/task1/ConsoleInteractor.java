package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.BookService;
import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;

import java.nio.file.Path;

/**
 * An actor given to user to interact with the program.
 */

public class ConsoleInteractor implements Runnable{

    private final StatisticsAttributeType attributeType;

    private final Path dir;

    public ConsoleInteractor(final Path dir,
                             final StatisticsAttributeType attributeType,
                             final BookService bookService,
                             final StatisticsService statisticsService){
        this.attributeType = attributeType;
        this.dir = dir;
    }

    @Override
    public void run() {
        System.out.println("The statistics for the attribute " + attributeType.getKey() + " have been established.");
        System.out.println("You can find the generated output in the "
                + dir +
                "\\output\\statistics_by_" + attributeType.getKey() + ".xml"
        );

        System.out.println("It isn't clear, should I be there?");
    }
}
