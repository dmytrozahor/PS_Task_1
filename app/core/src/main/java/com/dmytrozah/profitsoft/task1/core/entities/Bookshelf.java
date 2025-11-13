package com.dmytrozah.profitsoft.task1.core.entities;

import java.util.ArrayList;
import java.util.List;

public class Bookshelf {

    private String path;

    private List<Book> books = new ArrayList<>();

    public Bookshelf() {}

    public Bookshelf(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
