package com.dmytrozah.profitsoft.task1.core.entities.repository;

import com.dmytrozah.profitsoft.task1.core.entities.Book;
import com.dmytrozah.profitsoft.task1.mapping.exception.BookAlreadyExists;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public BookRepository() {

    }

    public void insert(final Book book) throws BookAlreadyExists {
        if (books.contains(book)) {
            throw new BookAlreadyExists(book);
        }

        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }


}
