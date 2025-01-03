package org.example.wiseSaying;

import java.io.IOException;
import java.util.ArrayList;
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

    public void printWiseSayingByKeyword(String keywordType, String keyword) {
        List<WiseSaying> wiseSayings = findAllWiseSayings();

        List<WiseSaying> filteredWiseSayings = new ArrayList<>();
        for (WiseSaying wiseSaying: wiseSayings) {
            switch (keywordType) {
                case "id":
                    if (String.valueOf(wiseSaying.getId()).equals(keyword)) {
                        filteredWiseSayings.add(wiseSaying);
                    }
                    break;
                case "content":
                    if (wiseSaying.getContent().contains(keyword)) {
                        filteredWiseSayings.add(wiseSaying);
                    }
                    break;
                case "author":
                    if (wiseSaying.getAuthor().contains(keyword)) {
                        filteredWiseSayings.add(wiseSaying);
                    }
                    break;
                default:
                    System.out.println("일치하는 키워드가 없습니다.");
                    return;
            }
        }

        if (filteredWiseSayings.isEmpty()) {
            System.out.println("해당 명언을 찾을 수 없습니다.");
        } else {
            for (int i = filteredWiseSayings.size() - 1; i >= 0; i--) {
                WiseSaying wiseSaying = filteredWiseSayings.get(i);
                System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
            }
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