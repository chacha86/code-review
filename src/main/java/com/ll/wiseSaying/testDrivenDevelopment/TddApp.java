package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.Scanner;

public class TddApp {

    private final TddWiseSayingController controller = TddWiseSayingController.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            String mainCommand = command.split("\\?")[0];

            switch (mainCommand) {
                case "등록" -> controller.register(scanner);
                case "목록" -> controller.search(scanner, command);
                case "수정" -> controller.modify(scanner, command);
                case "삭제" -> controller.delete(command);
                case "빌드" -> controller.build();
                case "종료" -> {
                    return;
                }
                default -> System.out.println("명령을 입력해주세요.");
            }
        }
    }
}
