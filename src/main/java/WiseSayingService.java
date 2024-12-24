import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    public WiseSaying create(String content, String author) {
        return wiseSayingRepository.create(content, author);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    public WiseSaying findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void remove(int id) {
        wiseSayingRepository.remove(id);
    }

    public void modify(int id, String content, String author) {
        wiseSayingRepository.modify(id, content, author);
    }

    public List<WiseSaying> search(String keywordType, String keyword) { return wiseSayingRepository.search(keywordType, keyword); }

    public void build() {
        wiseSayingRepository.build();
    }
}