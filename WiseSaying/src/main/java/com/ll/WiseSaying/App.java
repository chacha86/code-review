package com.ll.WiseSaying;

import com.ll.WiseSaying.Controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final WiseSayingController wiseSayingController = new WiseSayingController();
    public void run() {
        System.out.println("== 명령 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String command = sc.next();
            if(command.equals("종료")){
                break;
            }
            wiseSayingController.commandHandler(command);
        }
    }
}
