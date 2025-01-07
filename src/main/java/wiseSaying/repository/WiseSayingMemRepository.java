package wiseSaying.repository;

import wiseSaying.WiseSaying;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WiseSayingMemRepository implements WiseSayingRepository {
    private final List<WiseSaying> wiseSayingList;
    private int lastId;

    public WiseSayingMemRepository() {
        wiseSayingList = new ArrayList<>();
    }

    public WiseSaying save(WiseSaying wiseSaying) {
        if (!wiseSaying.isNew()) return wiseSaying;

        int id = ++lastId;
        wiseSaying.setId(id);
        wiseSayingList.add(wiseSaying);

        return wiseSaying;
    }

    public List<WiseSaying> findAll() {
        return wiseSayingList;
    }

    public boolean deleteOneById(int id) {
        return wiseSayingList.removeIf(wiseSaying -> id == wiseSaying.getId());
    }

    public Optional<WiseSaying> findOneById(int id) {
        return wiseSayingList.stream()
                .filter(wiseSaying -> id == wiseSaying.getId())
                .findFirst();
    }

}
