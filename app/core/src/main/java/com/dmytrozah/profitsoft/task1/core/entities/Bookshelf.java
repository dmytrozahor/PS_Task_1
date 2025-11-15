package com.dmytrozah.profitsoft.task1.core.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bookshelf {
    private final List<Book> books = new ArrayList<>();

    private String path;

    public Bookshelf() {}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bookshelf bookshelf = (Bookshelf) o;
        return Objects.equals(path, bookshelf.path) && Objects.equals(books, bookshelf.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, books);
    }
}
