package com.dmytrozah.profitsoft.task1.mapping.exception;

import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;

public class MalformedBookshelf extends RuntimeException {

    public MalformedBookshelf(Bookshelf bookshelf) {
        super("The bookshelf contains is malformed, the extraction process cannot be continued.");
    }
}
