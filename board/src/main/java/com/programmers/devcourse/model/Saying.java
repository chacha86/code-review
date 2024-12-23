package com.programmers.devcourse.model;


import com.programmers.devcourse.generator.SayingIdxGenerator;

import java.util.Objects;

public class Saying {
    private final String author;
    private final String contents;
    private final Long idx;
    public String getAuthor() {
        return author;
    }

    public String getContents() {
        return contents;
    }

    public Saying(String author, String contents) {
        this.author = author;
        this.contents = contents;
        this.idx=SayingIdxGenerator.getInstance().getIdx();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Saying saying = (Saying) o;
        return Objects.equals(idx, saying.idx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }
}
