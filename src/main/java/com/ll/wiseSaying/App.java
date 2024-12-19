package com.ll.wiseSaying;

import java.util.Scanner;

public class App {

    private final Scanner scanner = new Scanner(System.in);
    private final WiseSayingController wiseSayingController = WiseSayingController.getInstance();

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if (cmd.equals("종료")) {
                break;
            }

            wiseSayingController.handleCommandLine(cmd, scanner);
        }
    }
}
