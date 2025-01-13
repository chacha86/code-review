package wiseSaying;

import wiseSaying.repository.RepositoryProvider;
import wiseSaying.repository.WiseSayingFileRepository;
import wiseSaying.repository.WiseSayingRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        wiseSayingRepository = RepositoryProvider.provide();
    }

    public WiseSaying write(String content, String author) {

        WiseSaying wiseSaying = new WiseSaying(content, author);
        return wiseSayingRepository.save(wiseSaying);
    }

    public Page<WiseSaying> getAllItems(int itemsPerPage, int page) {
        return wiseSayingRepository.findAll(itemsPerPage, page);
    }

    public boolean delete(int id) {
        return wiseSayingRepository.deleteById(id);
    }

    public Optional<WiseSaying> getItem(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void modify(WiseSaying wiseSaying, String newContent, String newAuthor) {
        wiseSaying.setContent(newContent);
        wiseSaying.setAuthor(newAuthor);

        wiseSayingRepository.save(wiseSaying);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public Page<WiseSaying> search(String ktype, String kw, int itemsPerPage, int page) {
        return wiseSayingRepository.findByKeyword(ktype, kw, itemsPerPage, page);
    }

    public void makeSampleData(int cnt) {
        wiseSayingRepository.makeSampleData(cnt);
    }

    public int count() {
        return wiseSayingRepository.count();
    }
}