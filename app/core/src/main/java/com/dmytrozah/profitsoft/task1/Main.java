package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;
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

            FILE_PROCESSOR.init(
                    attributeType,
                    directory,
                    commandLine.hasOption(THREADS_OPTION) ?
                            Integer.parseInt(commandLine.getOptionValue(THREADS_OPTION)) : -1
            );


            final ConsoleInteractor interactor = new ConsoleInteractor(
                    Path.of(directory),
                    FILE_PROCESSOR,
                    attributeType,
                    FILE_PROCESSOR.getStatisticsService()
            );

            FILE_PROCESSOR.processInputFiles((output) -> {
                try {
                    FILE_PROCESSOR.processOutputFiles();
                    FILE_PROCESSOR.shutdown();

                    interactor.accept(output);
                } catch (Exception e) {
                    throw new RuntimeException("There was an error during the processing of files.", e);
                }

            }, latch);

        } catch (ParseException e) {
            System.err.println("Error while parsing command line arguments: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}