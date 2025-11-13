package com.dmytrozah.profitsoft.task1.mapping;

import com.dmytrozah.profitsoft.task1.core.StatisticsService;
import com.dmytrozah.profitsoft.task1.core.entities.Book;
import com.dmytrozah.profitsoft.task1.core.entities.BookFactory;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.Statistics;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsAttributeType;
import com.dmytrozah.profitsoft.task1.core.entities.statistics.StatisticsExport;
import com.dmytrozah.profitsoft.task1.mapping.exception.MalformedBookshelf;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EntityFileProcessor {

    static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static final XmlMapper XML_MAPPER = new XmlMapper();

    static final Map<String, Bookshelf> CACHE = new HashMap<>();

    static final String EXPORT_FILE_SUFFIX = "statistics_by_";

    private final BookFactory bookFactory = new BookFactory();

    private ExecutorService executor;

    private StatisticsService statisticsService;

    private Path inputDir;

    private Path outputDir;

    private int threads;

    private StatisticsAttributeType attributeType;

    /**
     *
     * @param statisticsService - an instance of {@link StatisticsService}
     * @param base - base directory
     * @param attributeType - an inner attribute type
     * @param threads - number of threads for the processing of files.
     */

    public void init(final StatisticsService statisticsService,
                     final String base,
                     final StatisticsAttributeType attributeType,
                     int threads) {
        this.statisticsService = statisticsService;
        this.attributeType = attributeType;

        this.inputDir = Paths.get(base, "/input/");
        this.outputDir = Paths.get(base, "/output/");

        if (threads == -1 || threads > Runtime.getRuntime().availableProcessors()) {
            this.threads = determineDefaultThreads(inputDir);
        } else {
            this.threads = threads;
        }

        this.executor = Executors.newFixedThreadPool(threads);
    }

    /**
     * Process input files with a statistic generation over the input data and then generating entities.
     *
     * @param outputConsumer - output consuming instance.
     */

    public void processInputFiles(final Consumer<List<Bookshelf>> outputConsumer,
                                  final CountDownLatch latch) {
        try {
            try (Stream<Path> pathStream = Files.list(inputDir)) {
                if (threads > 1) {
                    final List<CompletableFuture<Bookshelf>> tasks = pathStream
                            .filter(file -> !Files.isDirectory(file))
                            .map(file -> CompletableFuture
                                    .supplyAsync(() -> this.readBookshelf(file), executor)
                            )
                            .toList();

                    final CompletableFuture<List<Bookshelf>> allTasks = CompletableFuture
                            .allOf(tasks.toArray(new CompletableFuture[0]))
                            .thenApply(v -> tasks.stream()
                                    .map(CompletableFuture::join)
                                    .toList()
                            );

                    allTasks.whenComplete((result, ex) -> {
                        if (ex != null) {
                            throw new RuntimeException(ex);
                        } else {
                            result.forEach(b -> CACHE.put(b.getPath(), b));
                            outputConsumer.accept(result);

                            if (latch != null) {
                                try {
                                    latch.countDown();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                } else {
                    final List<Bookshelf> bookshelves = pathStream
                            .filter(file -> !Files.isDirectory(file))
                            .map(this::readBookshelf)
                            .toList();

                    bookshelves.forEach((bookshelf) -> CACHE.put(bookshelf.getPath(), bookshelf));

                    if (outputConsumer != null)
                        outputConsumer.accept(bookshelves);

                    if (latch != null)
                        latch.countDown();
                }

            }
        } catch (IOException e) {
            System.out.println("Error while getting the book shelves: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void processOutputFiles() {
        final Statistics aggregated = this.statisticsService.getAggregatedStatistics();

        final ArrayList<Map.Entry<String, Long>> sortedOccurrences = new ArrayList<>(
                        aggregated.getOccurrences().entrySet()
        );
        sortedOccurrences.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        final StatisticsExport export = StatisticsExport.ofEntryList(sortedOccurrences);

        XML_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValue(outputDir.resolve(EXPORT_FILE_SUFFIX + attributeType.getKey() + ".xml"), export);
    }

    public List<Book> extractBooks(final Bookshelf bookshelf, final long limit){
        if (Objects.isNull(bookshelf.getPath()))
            throw new MalformedBookshelf(bookshelf);

        final List<Book> books = new ArrayList<>();
        final Path path = Paths.get(bookshelf.getPath());

        try (MappingIterator<JsonNode> mappingIterator = JSON_MAPPER.readerFor(JsonNode.class).readValues(path.toFile())) {
            for (int i = 0; i < limit; i++) {
                final JsonNode node = mappingIterator.nextValue();

                books.add(bookFactory.createBook(node));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public Bookshelf readBookshelf(final Path path) {
        final Bookshelf bookshelf = new Bookshelf();
        bookshelf.setPath(path.toString());

        this.statisticsService.analyze(bookshelf, path);

        return bookshelf;
    }

    /**
     * Determines how many threads are to be used in order to achieve productive processing
     *
     * @implNote as we would not profit of context switch in the processor, maximum thread count
     * should be less than maximal available processor units.
     *
     * @param inputDir - input folder
     * @return optimal thread number
     */

    private int determineDefaultThreads(final Path inputDir){
        try (Stream<Path> inputFiles = Files.list(inputDir)){
            final int availableProcessors = Runtime.getRuntime().availableProcessors();
            int filesCount = (int) inputFiles.count();

            return Math.min(availableProcessors, filesCount); // Do not exceed maximum productivity limit
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getThreads() {
        return threads;
    }

    public void shutdown() {
        this.executor.shutdown();
    }
}
