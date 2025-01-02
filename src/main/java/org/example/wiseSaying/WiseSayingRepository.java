package org.example.wiseSaying;

import java.util.ArrayList;

public interface WiseSayingRepository {
    WiseSaying findById(int id);
    WiseSaying add(String content, String author);
    void update(WiseSaying wiseSaying);
    ArrayList<WiseSaying> findAll();
    void remove(WiseSaying wiseSaying);
}