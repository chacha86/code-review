package com.programmers.devcourse.model;


public class Saying {
    private String author;
    private String contents;
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

    public Saying modify(String newAuthor,String newContents){
        this.author = newAuthor;
        this.contents = newContents;
        return this;
    }
}
