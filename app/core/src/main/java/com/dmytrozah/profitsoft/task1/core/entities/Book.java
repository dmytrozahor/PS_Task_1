package com.dmytrozah.profitsoft.task1.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record Book(@JsonProperty("title") String title,
                   @JsonProperty("author") String author,
                   @JsonProperty("year_published") int yearPublished)
{
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return yearPublished == book.yearPublished && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, yearPublished);
    }
}
