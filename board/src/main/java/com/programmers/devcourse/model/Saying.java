package com.programmers.devcourse.model;


public class Saying {
    private final String author;
    private final String contents;
    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public Saying(String author, String contents) {
        this.author = author;
        this.contents = contents;
    }
}
