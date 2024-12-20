package com.ll.wiseSaying;

public class WiseSaying {
    int idx;
    String saying;
    String author;

    public WiseSaying(int idx, String saying, String author) {
        this.idx = idx;
        this.saying = saying;
        this.author = author;
    }

    public int getIdx() {
        return idx;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) { this.saying = saying; };

    public void setAuthor(String author) { this.author = author; };

    public String getAuthor() {
        return author;
    }
}
