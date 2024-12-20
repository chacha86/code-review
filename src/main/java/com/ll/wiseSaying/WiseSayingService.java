package com.ll.wiseSaying;

public class WiseSayingService {
    private final WiseSayingRepository repository;

    public WiseSayingService() {
        this.repository = new WiseSayingRepository();
    }

    public int addWiseSaying(String wiseSaying, String author) {
        return repository.save(new WiseSaying(repository.getNextId(), wiseSaying, author));
    }

    public CustomList getWiseSayings() {
        return repository.findAll();
    }

    public void deleteWiseSaying(int id) {
        repository.deleteById(id);
    }

    public void initWiseSayingService() {
        repository.init();
    }

    public CustomList searchWiseSayings(String keywordType, String keyword) {
        return repository.search(keywordType, keyword);
    }
}
