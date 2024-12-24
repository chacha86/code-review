package com.ll.wiseSaying.testDrivenDevelopment;

public class TddWiseSayingService {

    private final static TddWiseSayingService instance = new TddWiseSayingService();
    private TddWiseSayingService() {}
    public static synchronized TddWiseSayingService getInstance() {
        return instance;
    }

    private final TddWiseSayingRepository repository = TddWiseSayingRepository.getInstance();

    public int registerWiseSaying(String author, String content) {
        return repository.registerWiseSaying(author, content);
    }

    public TddPage<TddWiseSaying> findAll(int pageNum, int pageSize) {
        return repository.findAll(pageNum, pageSize);
    }

    public TddPage<TddWiseSaying> findDetail(int pageNum, int pageSize, String keywordType, String keyword) {
        if (keywordType.equals("content")) {
            return  repository.findAllByContent(pageNum, pageSize, keyword);
        } else if (keywordType.equals("author")) {
            return repository.findAllByAuthor(pageNum, pageSize, keyword);
        }

        return null;
    }
}
