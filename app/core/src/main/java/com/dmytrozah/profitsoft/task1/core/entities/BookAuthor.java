package com.dmytrozah.profitsoft.task1.core.entities;

public class BookAuthor {

    private String displayName;

    public BookAuthor(String displayName) {
        this.displayName = displayName;
    }

    public BookAuthor() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
