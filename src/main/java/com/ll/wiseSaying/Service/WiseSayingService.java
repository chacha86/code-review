package com.ll.wiseSaying.Service;

import com.ll.wiseSaying.Repository.WiseSayingRepository;
import com.ll.wiseSaying.WiseSaying;

import java.util.ArrayList;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public WiseSaying getItem(int targetId) {
        return wiseSayingRepository.findById(targetId);
    }

    public void modify(WiseSaying wiseSaying, String newContent, String newAuthor) {
        wiseSaying.setContent(newContent);
        wiseSaying.setAuthor(newAuthor);

        wiseSayingRepository.update(wiseSaying);
    }

    public WiseSaying write(String content, String author) {
        return wiseSayingRepository.add(content, author);
    }

    public ArrayList<WiseSaying> getItems() {
        return wiseSayingRepository.findAll();
    }

    public void remove(WiseSaying wiseSaying) {
        wiseSayingRepository.remove(wiseSaying);
    }
}
