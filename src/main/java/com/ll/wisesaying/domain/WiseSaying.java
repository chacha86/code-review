package com.ll.wisesaying.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WiseSaying implements Comparable<WiseSaying> {
    private int id;
    private String content;
    private String author;

    public WiseSaying() {
    }

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    @Override
    public int compareTo(WiseSaying o) {
        return Integer.compare(o.id, this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
