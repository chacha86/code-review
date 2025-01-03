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

    public void printWiseSaying(String command) {
        int page = 1;
        int itemsPerPage = 5;

        if (command.contains("?page=")) {
            try {
                page = Integer.parseInt(command.split("=")[1]);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 페이지 번호입니다.");
                return;
            }
        }

        List<WiseSaying> wiseSayings = findAllWiseSayings();

        wiseSayings.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));

        int totalItems = wiseSayings.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (page > totalPages) {
            System.out.println("잘못된 페이지 번호입니다.");
            return;
        }

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        for (int i = startIndex; i < endIndex; i++) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }

        System.out.println("----------------------");
        System.out.print("페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == page) {
                System.out.print("[" + i + "]");
            } else {
                System.out.print(" / " + i);
            }
        }
        System.out.println();
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