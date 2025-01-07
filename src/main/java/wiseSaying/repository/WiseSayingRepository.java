package wiseSaying.repository;

import wiseSaying.WiseSaying;

import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {
    WiseSaying save(WiseSaying wiseSaying);
    List<WiseSaying> findAll();
    Optional<WiseSaying> findOneById(int id);
    boolean deleteOneById(int id);

}
