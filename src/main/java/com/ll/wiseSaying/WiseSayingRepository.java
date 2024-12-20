package com.ll.wiseSaying;

public class WiseSayingRepository {
    private CustomList wiseSayings = new CustomList();
    private int lastId = 0;

    public int save(WiseSaying wiseSaying) {
        wiseSayings.add(wiseSaying);
        return wiseSaying.getIdx();
    }

    public void init() {
        wiseSayings = new CustomList();
    }

    public boolean deleteById(int id) {
        WiseSaying wiseSaying = wiseSayings.getWiseSaying(id);
        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return false;
        }
        wiseSayings.remove(id);

        return true;
    }

    public CustomList findAll() {
        return wiseSayings;
    }

    public int getNextId() {
        return ++lastId;
    }

    public CustomList search(String keywordType, String keyword) {
        return wiseSayings.searchByKeyword(keywordType, keyword);
    }


}
