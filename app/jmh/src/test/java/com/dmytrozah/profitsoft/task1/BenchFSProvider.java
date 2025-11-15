package com.dmytrozah.profitsoft.task1;

import com.dmytrozah.profitsoft.task1.mapping.reader.EntityFSProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class BenchFSProvider implements EntityFSProvider {

    static final Path FILE_PATH = Path.of("./data/benchmark/input/mediocre_bookshelve_1.json");

    private byte[] contents = new byte[]{};

    public void readFiles() throws IOException {
        this.contents = Files.readAllBytes(FILE_PATH);

        System.out.println("Read contents from the benchmark input file: " + this.contents.length + " bytes");
    }

    @Override
    public Stream<Path> listFiles(Path path) throws IOException {
        return Stream.of(FILE_PATH);
    }

    @Override
    public InputStream getInputStream(Path path) throws IOException {
        return new ByteArrayInputStream(contents);
    }
}
