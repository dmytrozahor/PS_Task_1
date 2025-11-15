package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.mapping.reader.DefaultFSProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BatchFSProvider extends DefaultFSProvider {

    static final Path DIR_PATH = Path.of("./data/mediocre/input");

    private List<Path> pathStream;

    private Map<String, String> contents = new HashMap<>();

    private int batchSize;

    public void readFiles() throws IOException {
        this.pathStream = super.listFiles(DIR_PATH).toList();
        this.contents = super.listFiles(DIR_PATH).map(path -> {
                        try {
                            return Map.entry(path.toString(), new String(Files.readAllBytes(path)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.batchSize = contents.values().stream()
                .map(String::length)
                .reduce(0, Integer::sum);

        System.out.println("Read contents from the benchmark input file: " + this.contents.size() + " files, total: " + this.batchSize);
    }

    @Override
    public Stream<Path> listFiles(Path path) throws IOException {
        return pathStream.stream();
    }

    @Override
    public InputStream getInputStream(Path path) throws IOException {
        return new ByteArrayInputStream(contents.get(path.toString()).getBytes());
    }
}
