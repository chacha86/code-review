package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.Scanner;

public class TddWiseSayingController {

    private final static TddWiseSayingController instance = new TddWiseSayingController();

    private TddWiseSayingController() {
    }

    public static synchronized TddWiseSayingController getInstance() {
        return instance;
    }

    private final TddWiseSayingService service = TddWiseSayingService.getInstance();

    public void register(Scanner scanner) {

        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        if (content.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("명언과 작가를 모두 입력해주세요.");
        }

        int id = service.registerWiseSaying(author, content);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void search(Scanner scanner, String command) {

        int pageSize = 5;
        int pageNum = 1;
        TddPage<TddWiseSaying> page = null;

        if (!command.contains("?")) {
            page = service.findAll(pageNum, pageSize);
        }

        System.out.println(page);
    }

    public void modify(Scanner scanner, String command) {

    }

    public void delete(String command) {

    }

    public void build() {

    }
}
