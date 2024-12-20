package com.ll.WiseSaying.Service;

import com.ll.WiseSaying.Domain.WiseSaying;
import com.ll.WiseSaying.Repository.WiseSayingRepository;

import java.util.List;
import java.util.stream.Collectors;

public class WiseSayingService {
    private final WiseSayingRepository repository = new WiseSayingRepository();

    public int registerWiseSaying(String content, String author) {
        return repository.save(new WiseSaying(author, content));
    }

    public List<WiseSaying> listWiseSayings() {
        return repository.findAll()
                .stream()
                .sorted((a, b) -> b.getId() - a.getId())
                .collect(Collectors.toList());
    }

    public boolean deleteWiseSaying(int id) {
        return repository.remove(id);
    }

    public WiseSaying getWiseSaying(int id) {
        return repository.findById(id);
    }

    public void modifyWiseSaying(int id, String content, String author) {
        WiseSaying w = new WiseSaying(content, author);
        w.setId(id);
        repository.update(w.getId(), w);
    }

    public void buildDataToJson() {
        repository.saveToJson();
    }
}
