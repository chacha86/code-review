package org.example;

import java.util.regex.Pattern;

public class WiseSaying {
    private static final Pattern NO_SPECIAL = Pattern.compile("[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\s]+");

    private int id;
    private String content;
    private String author;

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public void setId(int id){
        this.id = id;
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

    public boolean validate(){
        return NO_SPECIAL.matcher(content).matches() && NO_SPECIAL.matcher(author).matches();
    }

    public void update(String content, String author){
        this.author = author==null?this.author:author;
        this.content = content==null?this.content:content;
    }
}
