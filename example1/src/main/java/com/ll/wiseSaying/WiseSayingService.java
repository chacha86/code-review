package com.ll.wiseSaying;

import java.util.List;

public class WiseSayingService {

    public WiseSayingRepository repository = new WiseSayingRepository();

    public WiseSaying find(int num) {
        return repository.find(num);
    }
    public void create(int n, String author, String wiseSaying) {
        repository.create(n, author, wiseSaying);
    }

    public List<Integer> findAll() {
        return repository.keySet();
    }
    public int pageSize() { return (repository.getMapSize() - 1) / 5 + 1;}

    public void update(int contentNum, String author, String wiseSaying) {
        repository.update(contentNum, author, wiseSaying);
    }
    public boolean delete(int contentNum) {
        return repository.delete(contentNum);
    }
}
