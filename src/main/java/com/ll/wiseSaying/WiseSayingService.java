package com.ll.wiseSaying;

import java.util.List;

public class WiseSayingService {

    private final static WiseSayingService instance = new WiseSayingService();
    private WiseSayingService() {}
    public static synchronized WiseSayingService getInstance() {
        return instance;
    }

    private final WiseSayingRepository wiseSayingRepository = WiseSayingRepository.getInstance();

    public WiseSaying register(String content, String author) {
        return wiseSayingRepository.save(content, author);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    public List<WiseSaying> findAllByContent(String content) {
        return wiseSayingRepository.findAllByContent(content);
    }

    public List<WiseSaying> findAllByAuthor(String author) {
        return wiseSayingRepository.findAllByAuthor(author);
    }

    public List<WiseSaying> getPagedWiseSayings(int page, int size, List<WiseSaying> resultList) {
        return wiseSayingRepository.findAllPaginated(page, size, resultList);
    }

    public boolean deleteById(int id) {
        return wiseSayingRepository.deleteById(id);
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void update(int id, String content, String author) {
        wiseSayingRepository.update(id, content, author);
    }

    public void saveAll() {
        wiseSayingRepository.saveAll();
    }
}
