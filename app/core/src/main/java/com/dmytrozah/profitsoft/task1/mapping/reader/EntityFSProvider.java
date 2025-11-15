package com.dmytrozah.profitsoft.task1.mapping.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface EntityFSProvider {

    Stream<Path> listFiles(final Path path) throws IOException;

    InputStream getInputStream(final Path path) throws IOException;

}
