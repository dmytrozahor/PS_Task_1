package com.dmytrozah.profitsoft.task1.core;

import com.dmytrozah.profitsoft.task1.core.entities.Book;
import com.dmytrozah.profitsoft.task1.core.entities.BookAuthor;
import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;
import com.dmytrozah.profitsoft.task1.mapping.EntityFileProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private final Map<Bookshelf, List<Book>> entities = new HashMap<>();

    private final EntityFileProcessor processor;

    static final long DEFAULT_EXTRACTION_LIMIT = 50;

    public BookService(final EntityFileProcessor processor) {
        this.processor = processor;
    }

    /**
     * May be used for the continuous work with {@link Book} and {@link BookAuthor} instances
     *
     * @param bookshelf {@link Bookshelf}
     * @return loaded books
     */

    public List<Book> loadBooks(final Bookshelf bookshelf){
        if (!entities.containsKey(bookshelf)) {
            entities.put(bookshelf, List.of());
        }

        final List<Book> books = this.processor.extractBooks(bookshelf, DEFAULT_EXTRACTION_LIMIT);

        entities.get(bookshelf).addAll(books);
        return books;
    }
}
