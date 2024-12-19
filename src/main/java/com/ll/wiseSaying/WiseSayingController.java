package com.ll.wiseSaying;

import java.util.*;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService = new WiseSayingService();

    public void handleCommandLine(String cmd, Scanner scanner) {

        if (cmd.equals("등록")) {
            registerWiseSaying(scanner);
        } else if (cmd.startsWith("목록")) {
            searchWiseSaying(cmd);
        } else if (cmd.startsWith("삭제")) {
            deleteWiseSaying(cmd);
        } else if (cmd.startsWith("수정")) {
            updateWiseSaying(scanner, cmd);
        } else if (cmd.equals("빌드")) {
            buildData();
        }
    }

    private void registerWiseSaying(Scanner scanner) {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        WiseSaying wiseSaying = wiseSayingService.register(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    private void searchWiseSaying(String cmd) {
        List<WiseSaying> wiseSayingList = new ArrayList<>();

        if (cmd.contains("keyword") && cmd.contains("keywordType")) {
            String keywordType = cmd.split("keywordType=")[1].split("&")[0];
            String keyword = cmd.split("keyword=")[1].split("&")[0];

            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");

            if (keywordType.equals("content")) {
                wiseSayingList = wiseSayingService.findAllByContent(keyword);
            } else if (keywordType.equals("author")) {
                wiseSayingList = wiseSayingService.findAllByAuthor(keyword);
            }
        } else {
            wiseSayingList = wiseSayingService.findAll();
        }

        printPagedList(cmd, wiseSayingList);
    }

    private void printPagedList(String cmd, List<WiseSaying> wiseSayingList) {
        int pageSize = 5;
        int page = cmd.contains("page=") ? Integer.parseInt(cmd.split("page=")[1].split("&")[0]) : 1;

        List<WiseSaying> resultList = wiseSayingService.getPagedWiseSayings(page, pageSize, wiseSayingList);
        int totalPages = (int) Math.ceil((double) wiseSayingList.size()/pageSize);

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (WiseSaying wiseSaying : resultList) {
            System.out.printf("%d / %s / %s%n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
        System.out.println("----------------------");
        System.out.print("페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i != 1) {
                System.out.print("/ ");
            }

            if (i == page) {
                System.out.printf("[%d] ", i);
            } else {
                System.out.printf("%d ", i);
            }
        }
        System.out.println();
    }

    private void deleteWiseSaying(String cmd) {
        int id = Integer.parseInt(cmd.split("=")[1]);
        if (wiseSayingService.deleteById(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    private void updateWiseSaying(Scanner scanner, String cmd) {
        int id = Integer.parseInt(cmd.split("=")[1]);
        WiseSaying wiseSaying = wiseSayingService.findById(id);

        if (wiseSaying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + wiseSaying.getContent());
        System.out.print("명언 : ");
        String newContent = scanner.nextLine();
        System.out.println("작가(기존) : " + wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = scanner.nextLine();

        wiseSayingService.update(id, newContent, newAuthor);
    }

    private void buildData() {
        wiseSayingService.saveAll();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
