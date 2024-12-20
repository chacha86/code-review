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

    // 등록
    public Long register(String content, String author) {
        WiseSaying wise = WiseSaying.createWise(content, author);
        Long addedId = repository.add(wise);
        WiseSayingSaveFile.saveWiseByFile(repository.findById(addedId).orElseThrow(()-> new RuntimeException("해당 ID로 조회되는 명언이 없어서 FILE 저장 실패.")));
        WiseSayingSaveFile.updateLastId(addedId);
        return wise.getId();
    }

    // ID 로 삭제
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
            // 전체 목록 출력 : 조건 없음, page 설정 없음
            return repository.findAll();
        } else if (keywordType.equalsIgnoreCase("content")) {
            // keywordType == content
            return repository.findByContent(keyword);
        } else if (keywordType.equalsIgnoreCase("author")) {
            // keywordType == author
            return repository.findByAuthor(keyword);
        }
        return null;
    }

    // ID 로 단건 조회
    public WiseSaying searchOneWise(Long id) {
        try {
            return repository.findById(id).orElseThrow(() -> new RuntimeException("해당 ID로 조회되는 명언이 없음."));
        } catch (RuntimeException e) {
            return null;
        }
    }

    // ID 로 수정 후 파일 수정 및 저장
    public WiseSaying modify(Long id, String content, String author) {
        try {
            Long modifiedId = repository.modify(id, content, author);
            WiseSaying modifiedWise = repository.findById(modifiedId).orElseThrow(() -> new RuntimeException("해당 ID로 조회되는 명언이 없어서 FILE 저장 실패."));
            WiseSayingSaveFile.saveWiseByFile(modifiedWise);
            return modifiedWise;
        } catch (RuntimeException e) {
            log.info("service : 수정 실패");
            return null;
        }
    }

    //data.json file save
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
