package com.dmytrozah.profitsoft.task1.mapping.exception;

import com.dmytrozah.profitsoft.task1.core.entities.Bookshelf;

public class MalformedBookshelfException extends RuntimeException {

    public MalformedBookshelfException(Exception e, Bookshelf bookshelf) {
        super("The bookshelf content of " + bookshelf.getPath() + " is malformed, extraction process cannot be continued.", e);
    }
}
