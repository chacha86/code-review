package wiseSaying;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class WiseSayingRepository implements Repository {

    private static final WiseSayingRepository instance = new WiseSayingRepository();

    private WiseSayingRepository() {}

    public static synchronized WiseSayingRepository getInstance() {
        return instance;
    }

    private Set<WiseSaying> wiseSet = new HashSet<>();

    // db (in memory) 저장 후 저장된 ID 값 return
    public Long add(WiseSaying wise) {
        wiseSet.add(wise);
        return wise.getId();
    }

    // ID로 Wise 단건 조회
    public Optional<WiseSaying> findById(Long id) {
        return wiseSet.stream()
                .filter(wise -> wise.getId().equals(id))
                .findFirst();
    }

    // 내용(content)으로 Wise 객체 조회
    public Set<WiseSaying> findByContent(String content) {
        return wiseSet.stream()
                .filter(wise -> wise.getContent().contains(content))
                .collect(Collectors.toSet());
    }

    // 저자(author)로 Wise 객체 조회
    public Set<WiseSaying> findByAuthor(String author) {
        return wiseSet.stream()
                .filter(wise -> wise.getAuthor().contains(author))
                .collect(Collectors.toSet());
    }

    // ID 로 검색 후 수정, 수정 후 수정된 ID 값 return
    public Long modify(Long id, String content, String author) {
        try {
            WiseSaying modifiedWise = new WiseSaying(findById(id).orElseThrow(() -> new NoSuchElementException("수정 하고자 하는 명언을 찾을 수 없음.")).getId(), content, author);
            remove(modifiedWise.getId());
            return add(modifiedWise);
        } catch (NoSuchElementException e) {
            log.info("repository: ", e);
            throw new RuntimeException("repository : 수정 실패");
        }
    }

    // 전체 목록 조회
    public Set<WiseSaying> findAll() {
        return wiseSet;
    }

    // ID 로 삭제 후 삭제된 ID 값 return
    public Long remove(Long id) {
        wiseSet.remove(findById(id).orElseThrow(() -> new RuntimeException("삭제하고자 하는 명언을 찾을 수 없음.")));
        return id;
    }

    public void clear() {
        wiseSet.clear();
    }
}
