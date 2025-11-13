package com.dmytrozah.profitsoft.task1.core;

import com.dmytrozah.profitsoft.task1.core.entities.Book;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {
    private final Map<String, List<Book>> entities = new HashMap<>();

    private final EntityFileProcessor processor;

    static final long DEFAULT_EXTRACTION_LIMIT = 50;

    public BookService(final EntityFileProcessor processor) {
        this.processor = processor;
    }

    /**
     * May be used for the continuous work with {@link Book} instance
     *
     * @param bookshelf {@link Bookshelf}
     * @return loaded books
     */

    public List<Book> loadBooks(final Bookshelf bookshelf){
        if (!entities.containsKey(bookshelf.getPath())) {
            entities.put(bookshelf.getPath(), new ArrayList<>());
        }

        final List<Book> books = this.processor.extractBooks(bookshelf, DEFAULT_EXTRACTION_LIMIT);

        entities.get(bookshelf.getPath()).addAll(books);

        return books;
    }
}
