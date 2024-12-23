package com.programmers.devcourse4.wisesaying;

public class WiseSaying {

    private static int count = 0; //자동으로 number 1값 늘리기
    private int id;
    private String saying;
    private String author;

    public WiseSaying(String saying, String author) {
        this.id = ++count;
        this.saying = saying;
        this.author = author;
    }

    public static int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
