package com.ll.wisesaying.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WiseSaying implements Comparable<WiseSaying> {
    @JsonProperty("id")
    private int idx;
    @JsonProperty("content")
    private String sentence;
    @JsonProperty("author")
    private String writer;

    public WiseSaying() {
    }

    public WiseSaying(int idx, String sentence, String writer) {
        this.idx = idx;
        this.sentence = sentence;
        this.writer = writer;
    }

    @Override
    public int compareTo(WiseSaying o) {
        return Integer.compare(o.idx, this.idx);
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
