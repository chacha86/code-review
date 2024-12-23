package com.example.wisesaying.domain;

import lombok.*;

@Getter
@Setter
public class WiseSaying {

    private long id;
    private String content;
    private String author;

    public WiseSaying(long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

}
