package com.ll.wiseSaying;

import java.util.*;
import java.util.stream.Collectors;

public class WiseSayingJSONRepository implements WiseSayingRepository {
    private static final String DATA_FILE_PATH = "db/wiseSaying/";
    private static final String LAST_ID_FILE = "lastId.txt";
    private static final String BUILD_FILE = "data.json";
    private WiseSayingJSONMapper wiseSayingJSONMapper;
    private Map<Long, WiseSaying> wiseSayingMap;
    private long idCounter;

    public WiseSayingJSONRepository() {
        wiseSayingMap = new HashMap<>();
        wiseSayingJSONMapper = new WiseSayingJSONMapper();
        idCounter = 1L;
        loadJSON();
    }

    private void loadJSON() {
        List<String> wiseSayingJSONs = FileUtil.loadJSON(DATA_FILE_PATH);
        String loadedIDCounter = FileUtil.readTextFile(DATA_FILE_PATH + LAST_ID_FILE);
        if (loadedIDCounter != null && loadedIDCounter.matches("^\\d+$")) {
            idCounter = Long.parseLong(loadedIDCounter);
        }
        for (String wiseSayingJSON : wiseSayingJSONs) {
            WiseSaying wiseSaying = wiseSayingJSONMapper.toObject(wiseSayingJSON);
            wiseSayingMap.put(wiseSaying.getId(), wiseSaying);
            if (idCounter <= wiseSaying.getId()) {
                idCounter = wiseSaying.getId() + 1;
            }
            FileUtil.saveFile(DATA_FILE_PATH + LAST_ID_FILE, String.valueOf(idCounter));
        }
    }

    @Override
    public WiseSaying save(final WiseSaying wiseSaying) {
        wiseSaying.setId(idCounter);
        FileUtil.saveFile(
                DATA_FILE_PATH + wiseSaying.getId() + ".json",
                wiseSayingJSONMapper.toJSON(wiseSaying)
        );
        wiseSayingMap.put(idCounter++, wiseSaying);
        FileUtil.saveFile(DATA_FILE_PATH + LAST_ID_FILE, String.valueOf(idCounter));
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
        FileUtil.delete(DATA_FILE_PATH + wiseSayingID + ".json");
        wiseSayingMap.remove(wiseSayingID);
        return wiseSayingID;
    }

    @Override
    public WiseSaying update(final Long wiseSayingID, final WiseSaying wiseSaying) {
        wiseSaying.setId(wiseSayingID);
        FileUtil.saveFile(
                DATA_FILE_PATH + wiseSayingID + ".json",
                wiseSayingJSONMapper.toJSON(wiseSaying)
        );
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
        FileUtil.saveFile(BUILD_FILE, wiseSayingJSONMapper.toJSON(wiseSayingMap));
        return BUILD_FILE;
    }
}
