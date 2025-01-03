package org.example.wiseSaying;

import java.io.IOException;
import java.util.List;

/**
 *  서비스
 */

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void addWiseSaying(WiseSaying wiseSaying) throws IOException {
        wiseSayingRepository.saveWiseSaying(wiseSaying);
        wiseSayingRepository.saveWiseSayingToFile(wiseSaying);
        wiseSayingRepository.saveLastId(wiseSaying);
    }

    public List<WiseSaying> findAllWiseSayings() {
        return wiseSayingRepository.findAllWiseSayings();
    }

    public WiseSaying findWiseSayingById(int id) {
        return wiseSayingRepository.findWiseSayingById(id);
    }

    public void printWiseSaying() {
        List<WiseSaying> wiseSayings = findAllWiseSayings();

        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    public void updateWiseSaying(WiseSaying wiseSaying, String newContent, String newAuthor) throws IOException {
        wiseSaying.setContent(newContent);
        wiseSaying.setAuthor(newAuthor);
        wiseSayingRepository.saveWiseSaying(wiseSaying);
        wiseSayingRepository.saveWiseSayingToFile(wiseSaying);
        wiseSayingRepository.saveLastId(wiseSaying);
    }

    public boolean deleteWiseSaying(int id) throws IOException {
        return wiseSayingRepository.deleteWiseSayingById(id);
    }
}