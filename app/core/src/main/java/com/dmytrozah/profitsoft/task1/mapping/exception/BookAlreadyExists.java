package com.dmytrozah.profitsoft.task1.mapping.exception;

import com.dmytrozah.profitsoft.task1.core.entities.Book;

public class BookAlreadyExists extends Throwable {

    public BookAlreadyExists(final Book book) {
        super("Book already exists: " + book.getTitle());
    }
}
