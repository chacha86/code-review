package com.ll.wiseSaying.Repository;

import com.ll.wiseSaying.WiseSaying;

import java.util.ArrayList;

public class WiseSayingMemRepository implements WiseSayingRepository {
    private final ArrayList<WiseSaying> wiseSayingList = new ArrayList<>();
    private int lastId = 0;

    public WiseSaying findById(int targetId) {
        for (WiseSaying wiseSaying : wiseSayingList) {
            if (wiseSaying.getId() == targetId) {
                return wiseSaying;
            }
        }
        return null;
    }

    public WiseSaying add(String content, String author) {
        int id = ++lastId;
        WiseSaying wiseSaying = new WiseSaying(id, content, author);
        wiseSayingList.add(wiseSaying);
        return wiseSaying;
    }

    public ArrayList<WiseSaying> findAll() {
        return wiseSayingList;
    }

    public void remove(WiseSaying wiseSaying) {
        wiseSayingList.remove(wiseSaying);
    }

    public void update(WiseSaying wiseSaying) {
        // 현재는 메모리에 저장하기 때문에 별도의 업데이트 코드 없음
    }
}
