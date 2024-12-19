package wiseSaying;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.NoSuchElementException;
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

    public Long add(WiseSaying wise) {
        wiseSet.add(wise);
        return wise.getId();
    }

    // ID로 Wise 객체 조회
    public WiseSaying findById(Long id) {
        return wiseSet.stream()
                .filter(wise -> wise.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 ID 로 조회되는 명언 없음."));
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

    public Long modify(Long id, String content, String author) {
        try {
            WiseSaying modifiedWise = new WiseSaying(findById(id).getId(), content, author);
            remove(modifiedWise.getId());
            return add(modifiedWise);
        } catch (NoSuchElementException e) {
            log.info("repository: ", e);
            throw new RuntimeException("repository : 수정 실패");
        }
    }

    public Set<WiseSaying> findAll() {
        return wiseSet;
    }

    public Set<WiseSaying> getWiseSet() {
        return wiseSet;
    }

    public Long remove(Long id) {
        wiseSet.remove(findById(id));
        return id;
    }
}
