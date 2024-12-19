package wiseSaying;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class WiseSayingService {

    private static final WiseSayingService instance = new WiseSayingService();
    private WiseSayingService(){}
    public static synchronized WiseSayingService getInstance() {
        return instance;
    }

    private WiseSayingRepository repository = WiseSayingRepository.getInstance();

    public Long register(String content, String author) {
        WiseSaying wise = WiseSaying.createWise(content, author);
        Long addedId = repository.add(wise);
        WiseSayingSaveFile.saveWiseByFile(repository.findById(addedId));
        WiseSayingSaveFile.updateLastId(addedId);
        return wise.getId();
    }

    public Set<WiseSaying> findAllWise() {
        return repository.findAll();
    }

    public Long remove(Long id) {
        try {
            return repository.remove(id);
        } catch (RuntimeException e) {
            log.info("service: 해당 명언을 찾을 수 없음.");
            return null;
        }
    }

    public Set<WiseSaying> search(String keywordType, String keyword) {
        if (keywordType == null && keyword == null) {
            return repository.findAll();
        } else if (keywordType.equalsIgnoreCase("content")) {
            return repository.findByContent(keyword);
        } else if (keywordType.equalsIgnoreCase("author")) {
            return repository.findByAuthor(keyword);
        }
        return null;
    }

    public WiseSaying searchOneWise(Long id) {
        try {
            return repository.findById(id);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public WiseSaying modify(Long id, String content, String author) {
        try {
            Long modifiedId = repository.modify(id, content, author);
            WiseSayingSaveFile.saveWiseByFile(repository.findById(modifiedId));
            return repository.findById(modifiedId);
        } catch (RuntimeException e) {
            log.info("service : 수정 실패");
            return null;
        }
    }

    public Boolean build() {
        Set<WiseSaying> allWise = repository.findAll();
        try {
            WiseSayingSaveFile.buildFile(allWise);
            return true;
        } catch (RuntimeException e) {
            return false;
        }

    }

}
