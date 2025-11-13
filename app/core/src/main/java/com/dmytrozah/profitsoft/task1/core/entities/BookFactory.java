package com.dmytrozah.profitsoft.task1.core.entities;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public final class BookFactory {

    static Map<String, BookAuthor> CACHED_AUTHORS = new HashMap<>();

    public Book createBook(final JsonNode node) {
        final Book book = new Book();
        final BookAuthor author = CACHED_AUTHORS.getOrDefault(node.get("author").asText(), new BookAuthor());

        final String authorName = node.get("author").asText();

        book.setTitle(node.get("title").asText());
        book.setAuthor(author);

        if (!CACHED_AUTHORS.containsKey(authorName)) {
            CACHED_AUTHORS.put(authorName, author);
        }

        return book;
    }
}
