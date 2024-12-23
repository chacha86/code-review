package com.ll.wiseSaying;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class WiseSayingMapRepository implements WiseSayingRepository {
    private Map<Long, WiseSaying> wiseSayingMap;
    private long idCounter;

    public WiseSayingMapRepository() {
        wiseSayingMap = new HashMap<>();
        idCounter = 1L;
    }

    @Override
    public WiseSaying save(final WiseSaying wiseSaying) {
        wiseSaying.setId(idCounter);
        wiseSayingMap.put(idCounter++, wiseSaying);
        return wiseSaying;
    }

    @Override
    public WiseSaying findById(final Long wiseSayingID) {
        if (wiseSayingMap.containsKey(wiseSayingID)) {
            return wiseSayingMap.get(wiseSayingID);
        }
        throw new NoSuchElementException("존재하지 않는 명언 ID를 조회했습니다.");
    }

    @Override
    public long delete(final Long wiseSayingID) {
        wiseSayingMap.remove(wiseSayingID);
        return wiseSayingID;
    }

    @Override
    public WiseSaying update(final Long wiseSayingID, final WiseSaying wiseSaying) {
        wiseSaying.setId(wiseSayingID);
        wiseSayingMap.put(wiseSayingID, wiseSaying);
        return wiseSaying;
    }

    @Override
    public WiseSayingPage findByAuthorName(final String searchAuthorKeyword, final Long page, final int pageSize) {
        WiseSayingPage wiseSayingPage = new WiseSayingPage();
        wiseSayingPage.totalPage((wiseSayingMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getAuthor().contains(searchAuthorKeyword))
                .count() - 1) / pageSize + 1
        );
        wiseSayingPage.currentPage(page);
        wiseSayingPage.addWiseSayingList(wiseSayingMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getAuthor().contains(searchAuthorKeyword))
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .map(Map.Entry::getValue)
                .collect(Collectors.toUnmodifiableList())
        );
        return wiseSayingPage;
    }

    @Override
    public WiseSayingPage findByContent(final String searchContentKeyword, final Long page, final int pageSize) {
        WiseSayingPage wiseSayingPage = new WiseSayingPage();
        wiseSayingPage.totalPage((wiseSayingMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getContent().contains(searchContentKeyword))
                .count() - 1) / pageSize + 1
        );
        wiseSayingPage.currentPage(page);
        wiseSayingPage.addWiseSayingList(wiseSayingMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getContent().contains(searchContentKeyword))
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .map(Map.Entry::getValue)
                .collect(Collectors.toUnmodifiableList())
        );
        return wiseSayingPage;
    }

    @Override
    public WiseSayingPage findPage(final Long page, final int pageSize) {
        WiseSayingPage wiseSayingPage = new WiseSayingPage();
        wiseSayingPage.totalPage((wiseSayingMap.entrySet()
                .stream()
                .count() - 1) / pageSize + 1
        );

        wiseSayingPage.currentPage(page);
        wiseSayingPage.addWiseSayingList(wiseSayingMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .map(Map.Entry::getValue)
                .collect(Collectors.toUnmodifiableList())
        );
        return wiseSayingPage;
    }

    @Override
    public String build() {
        return null;
    }
}
