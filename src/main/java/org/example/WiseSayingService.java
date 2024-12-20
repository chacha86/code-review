package org.example;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository repository;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public WiseSaying create(String content, String author) {
        WiseSaying lifeQuote = new WiseSaying(content, author);
        return repository.insert(lifeQuote);
    }

    public List<WiseSaying> findAll() {
        return repository.findAll();
    }
    public Page findAllByPage(int pageNum) {
        List<WiseSaying> res = repository.findAll();
        int fromIdx = (pageNum-1)*5;
        int toIdx = Math.min((pageNum) * 5,res.size());
        return new Page(res.subList(fromIdx, toIdx), res.size(), 5, pageNum);
    }

    public void remove(int id) {
        repository.remove(id);
    }

    public WiseSaying find(int id) {
        return repository.find(id);
    }

    public void update(int id, WiseSaying wiseSaying) {
        repository.update(id, wiseSaying);
    }

    public void build() {
        repository.build();
    }

    public List<WiseSaying> findBy(String type, String word) {
        return repository.findBy(type, word);
    }
    public Page findByPage(String type, String word, int pageNum){
        List<WiseSaying> res = repository.findBy(type, word);
        int fromIdx = (pageNum-1)*5;
        int toIdx = Math.min((pageNum) * 5,res.size());
        return new Page(res.subList(fromIdx, toIdx), res.size(), 5, pageNum);
    }
}
