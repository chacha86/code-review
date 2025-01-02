package org.example.wiseSaying;

import java.util.ArrayList;

public class WiseSayingFileRepository implements WiseSayingRepository{

    // 오버라이딩
    public WiseSaying findById(int id) {
        return null;
    }

    @Override
    public WiseSaying add(String content, String author) {
        return null;
    }

    @Override
    public void update(WiseSaying wiseSaying) {

    }

    @Override
    public ArrayList<WiseSaying> findAll() {
        return null;
    }

    @Override
    public void remove(WiseSaying wiseSaying) {

    }

}