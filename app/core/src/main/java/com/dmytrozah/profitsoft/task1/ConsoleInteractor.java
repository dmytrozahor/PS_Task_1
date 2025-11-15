package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.BookService;
import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Book;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

/**
 * An actor given to user to interact with the program.
 */

public class ConsoleInteractor implements Consumer<List<Bookshelf>> {

    private final BookService bookService;
    private final EntityFileProcessor processor;
    private final StatisticsAttributeType attributeType;

    private final Path dir;

    public ConsoleInteractor(final Path dir,
                             final EntityFileProcessor processor,
                             final StatisticsAttributeType attributeType,
                             final StatisticsService statisticsService){
        this.attributeType = attributeType;
        this.dir = dir;

        this.processor = processor;
        this.bookService = new BookService(processor);
    }

    @Override
    public void accept(List<Bookshelf> initialList) {
        System.out.println("The statistics for the attribute " + attributeType.getKey() + " have been established.");
        System.out.println("You can find the generated output in the "
                + dir.resolve("output").resolve("statistics_by_" + attributeType.getKey() + ".xml")
        );

        System.out.println();
        initialList.forEach(bookshelf -> {
            final List<Book> books = bookService.loadBooks(bookshelf);

            System.out.println("Extraction of "
                            + books.size()
                            + " books from "
                            + bookshelf.getPath()
                            + " is successfully completed.");
                });

        System.out.println();

        System.out.println("It isn't clear, should I be there?");
    }
}
