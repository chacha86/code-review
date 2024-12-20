package com.ll.wiseSaying;

import java.util.*;

public class WiseSayingService {
    private final WiseSayingRepository repository;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public WiseSaying create(String content, String author) {
        int id = repository.getLastId();
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        repository.save(wiseSaying);
        repository.saveLastId(id + 1);
        return wiseSaying;
    }

    public PageResponse<WiseSaying> getList(int page, String keywordType, String keyword) {
        List<WiseSaying> wiseSayings;

        // 검색 조건이 있는 경우
        if (keywordType != null && !keywordType.isEmpty() && keyword != null && !keyword.isEmpty()) {
            wiseSayings = search(keywordType, keyword);
        } else {
            wiseSayings = repository.findAll();
        }

        // 최신순 정렬
        wiseSayings.sort((a, b) -> b.getId() - a.getId());

        // 페이징 처리
        int pageSize = PageResponse.getPageSize();
        int totalPages = (int) Math.ceil((double) wiseSayings.size() / pageSize);
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, wiseSayings.size());

        List<WiseSaying> pagedItems = wiseSayings.subList(startIndex, endIndex);
        return new PageResponse<>(pagedItems, page, totalPages);
    }

    private List<WiseSaying> search(String keywordType, String keyword) {
        List<WiseSaying> allWiseSayings = repository.findAll();
        List<WiseSaying> searchResults = new ArrayList<>();

        for (WiseSaying wiseSaying : allWiseSayings) {
            if (keywordType.equals("content") &&
                    wiseSaying.getContent().contains(keyword)) {
                searchResults.add(wiseSaying);
            } else if (keywordType.equals("author") &&
                    wiseSaying.getAuthor().contains(keyword)) {
                searchResults.add(wiseSaying);
            }
        }

        return searchResults;
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    public WiseSaying modify(int id, String content, String author) {
        WiseSaying wiseSaying = repository.findById(id);
        if (wiseSaying != null) {
            wiseSaying.setContent(content);
            wiseSaying.setAuthor(author);
            repository.save(wiseSaying);
        }
        return wiseSaying;
    }

    public void buildData() {
        repository.buildData();
    }
}