package com.ll.wiseSaying.src;

import org.json.simple.JSONObject;

public class WiseSaying {
    int id;
    String content;
    String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("quote", this.content);
        obj.put("author", this.author);
        return obj;
    }
}
