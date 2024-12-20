package com.ll.wiseSaying;

import java.util.Scanner;

public class App {
    private final Scanner scanner;
    private final WiseSayingController controller;

    public App() {
        this.scanner = new Scanner(System.in);
        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingService service = new WiseSayingService(repository);
        this.controller = new WiseSayingController(service, scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("등록")) {
                controller.create();
            } else if (command.equals("목록") || command.startsWith("목록?")) {
                controller.list(command);
            } else if (command.startsWith("삭제?id=")) {
                controller.delete(command);
            } else if (command.startsWith("수정?id=")) {
                controller.modify(command);
            } else if (command.equals("빌드")) {
                controller.build();
            } else if (command.equals("종료")) {
                break;
            } else {
                System.out.println("알 수 없는 명령");
            }
        }
    }
}