package com.ll.WiseSaying.Domain;

public class WiseSaying {
    public int id;
    public String author;
    public String content;

    public WiseSaying(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(int number) {
        this.id = number;
    }
}
