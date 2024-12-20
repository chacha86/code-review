package com.ll.wiseSaying;

import java.util.Scanner;

public class App {
    private final WiseSayingController controller;

    public App() {
        this.controller = new WiseSayingController();
    }

    public void run() {

        String command;
        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");
        while(true) {
            System.out.print("명령) ");
            command = sc.nextLine();

            if (command.equals("종료")) break;
            else controller.start(command);
        }

    }
}

