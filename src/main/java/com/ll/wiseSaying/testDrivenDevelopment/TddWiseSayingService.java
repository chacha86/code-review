package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TddWiseSayingService {

    private final TddWiseSayingRepository repository;

    public TddWiseSayingService(TddWiseSayingRepository repository) {
        this.repository = repository;
    }

    public int registerWiseSaying(String author, String content) {
        return repository.registerWiseSaying(author, content);
    }

    public TddPage<TddWiseSaying> findAll(int pageNum, int pageSize) {
        return repository.findAll(pageNum, pageSize);
    }

    public TddPage<TddWiseSaying> findDetail(int pageNum, int pageSize, String keywordType, String keyword) {
        if (keywordType.equals("content")) {
            return  repository.findAllByContent(pageNum, pageSize, keyword);
        } else if (keywordType.equals("author")) {
            return repository.findAllByAuthor(pageNum, pageSize, keyword);
        }

        return null;
    }

    public Map<String, String> parseSearchKeyword(String command) {
        Map<String, String> result = new HashMap<>();
        if (!command.contains("?")) return result;

        String[] splitCommand = command.split("\\?")[1].split("&");

        for (String s : splitCommand) {
            result.put(s.split("=")[0], s.split("=")[1]);
        }

        return result;
    }

    public boolean validateSearchKeyword(Map<String, String> keywordList) {
        if (keywordList.isEmpty()) return true;

        Set<String> allowedKeys = Set.of("page", "keyword", "keywordType");

        for (String key : keywordList.keySet()) {
            if (!allowedKeys.contains(key)) {
                return false;
            }
        }

        return keywordList.containsKey("keywordType") == keywordList.containsKey("keyword");
    }

    public int parseId(String command) {
        return Integer.parseInt(command.split("id=")[1]);
    }

    public TddWiseSaying findById(int id) {
        return repository.findById(id);
    }

    public void modifyWiseSaying(int id, String author, String content) {
        repository.modifyWiseSaying(id, author, content);
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public boolean saveAll() {
        return repository.saveAll();
    }
}
