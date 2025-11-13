package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.BookService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

public class Main {

    static final Option DIRECTORY_OPTION = Option.builder("d")
            .type(String.class)
            .longOpt("directory")
            .desc(
                    """
                    Specifies the base directory from which the bookshelves should be read
                    """)
            .hasArg()
            .required()
            .get();

    static final Option THREADS_OPTION = Option.builder("t")
            .type(Number.class)
            .longOpt("threads")
            .desc("""
                    Specifies the number of threads program should use to process files
                    """)
            .optionalArg(true)
            .hasArg()
            .get();

    static final Option ATTRIBUTE_OPTION = Option.builder("attr")
            .type(String.class)
            .longOpt("attribute")
            .desc("""
                    Specifies the name of the attribute used to process files
                    """)
            .hasArg()
            .required()
            .get();

    static final EntityFileProcessor FILE_PROCESSOR = new EntityFileProcessor();

    public static void main(String[] args) {
        Main.readParams(args);
    }

    static void readParams(final String[] args){
        final Options option = new Options()
                .addOption(DIRECTORY_OPTION)
                .addOption(THREADS_OPTION)
                .addOption(ATTRIBUTE_OPTION);

        try {
            CommandLine commandLine = new DefaultParser().parse(option, args);

            final CountDownLatch latch = new CountDownLatch(1); // Latch used for the benchmarking

            final String directory = commandLine.getOptionValue(DIRECTORY_OPTION);
            final String attributeKey = commandLine.getOptionValue(ATTRIBUTE_OPTION);

            final StatisticsAttributeType attributeType = StatisticsAttributeType.getByKey(attributeKey)
                    .orElseThrow(IllegalArgumentException::new);

            final StatisticsService statisticsService = new StatisticsService(attributeType);

            final BookService bookService = new BookService(FILE_PROCESSOR);
            final ConsoleInteractor interactor = new ConsoleInteractor(
                    Path.of(directory),
                    attributeType,
                    bookService,
                    statisticsService
            );

            FILE_PROCESSOR.init(
                    statisticsService,
                    directory,
                    attributeType,
                    commandLine.hasOption(THREADS_OPTION) ?
                            Integer.parseInt(commandLine.getOptionValue(THREADS_OPTION)) : -1,
                    interactor,
                    latch
            );
        } catch (ParseException e) {
            System.err.println("Error while parsing command line arguments: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}