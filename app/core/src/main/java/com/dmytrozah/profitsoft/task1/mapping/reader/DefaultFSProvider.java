package com.dmytrozah.profitsoft.task1.mapping.reader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DefaultFSProvider implements EntityFSProvider {

    @Override
    public Stream<Path> listFiles(Path path) throws IOException {
        final DirectoryStream<Path> stream = Files.newDirectoryStream(path);

        return StreamSupport.stream(stream.spliterator(), false).onClose(() -> {
            try {
                stream.close();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public InputStream getInputStream(Path path) throws IOException {
        return new BufferedInputStream(Files.newInputStream(path));
    }
}
