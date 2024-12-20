package com.ll.wiseSaying;

public class WiseSaying {
    private int number;
    private String author;
    private String wiseSaying;

    public WiseSaying(int number, String author, String wiseSaying) {
        this.number = number;
        this.author = author;
        this.wiseSaying = wiseSaying;
    }

    public int getNumber() {
        return number;
    }

    public String getAuthor() {
        return author;
    }

    public String getWiseSaying() {
        return wiseSaying;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setWiseSaying(String wiseSaying) {
        this.wiseSaying = wiseSaying;
    }

    @Override
    public String toString() {
        return  number + " / " + author + " / " + wiseSaying;
    }
}
