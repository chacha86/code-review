package com.ll.wiseSaying;

import java.util.Scanner;

public class App {

    private final Scanner scanner = new Scanner(System.in);
    private final WiseSayingController controller = WiseSayingController.getInstance();

    private enum Command {
        REGISTER("등록"),
        SEARCH("목록"),
        DELETE("삭제"),
        MODIFY("수정"),
        BUILD("빌드"),
        EXIT("종료");

        final String cmd;

        Command(String cmd) {
            this.cmd = cmd;
        }

        public static Command of(String cmd) {
            for (Command command : Command.values()) {
                if (command.cmd.equals(cmd))
                    return command;
            }
            throw new IllegalArgumentException("잘못된 명령입니다.");
        }
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            Command mainCmd = Command.of(cmd.split("\\?")[0]);
            if (mainCmd.equals(Command.EXIT)) {
                break;
            }

            switch (mainCmd) {
                case REGISTER-> controller.registerWiseSaying(scanner);
                case SEARCH -> controller.searchWiseSaying(cmd);
                case DELETE -> controller.deleteWiseSaying(cmd);
                case MODIFY -> controller.updateWiseSaying(scanner, cmd);
                case BUILD -> controller.buildData();
            }
        }
    }
}
