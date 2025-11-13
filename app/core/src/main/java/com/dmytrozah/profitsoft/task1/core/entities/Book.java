package com.dmytrozah.profitsoft.task1.core.entities;

public class Book {

    private String title;

    private BookAuthor author;

    public Book(String title, BookAuthor author) {
        this.title = title;
        this.author = author;
    }

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookAuthor getAuthor() {
        return author;
    }

    public void setAuthor(BookAuthor author) {
        this.author = author;
    }
}
