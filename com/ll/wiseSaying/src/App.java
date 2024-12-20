package com.ll.wiseSaying.src;

import java.util.Scanner;

public class App {
    public Scanner sc;
    static String[] commands = {"등록", "목록", "빌드", "종료"};
    static String patternDelete = "삭제\\?id=\\d+";
    static String patternUpdate = "수정\\?id=\\d+";

    static boolean commandNeedId = false;

    WiseSayingController wiseSayingController;

    public void run(Scanner sc) {

        this.sc = sc;
        wiseSayingController = new WiseSayingController(sc);
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.printf("명령) ");
            String commandInput = sc.nextLine();
            String command = "";
            int id = 0;
            if(!validCommand(commandInput)) {
                System.out.println("올바른 명령어가 아닙니다.");
                continue;
            }
            if(commandNeedId){
                command = commandInput.split("\\?")[0];
                id = Integer.parseInt(commandInput.split("=")[1]);
            }else{
                command = commandInput;
            }
            switch (command) {
                case "등록":
                    wiseSayingController.addWiseSaying();
                    break;
                case "목록":
                    wiseSayingController.showWiseSaying();
                    break;
                case "빌드":
                    wiseSayingController.buildJson();
                    break;
                case "삭제":
                    wiseSayingController.removeWiseSaying(id);
                    break;
                case "수정":
                    wiseSayingController.updateWiseSaying(id);
                    break;
                case "종료":
                    wiseSayingController.terminate();
                    sc.close();
                    return;
            }
        }
    }

    public boolean validCommand(String command) {
        if(command.length() == 2) {
            commandNeedId = false;
            for (String c : commands) {
                if (c.equals(command)) {
                    return true;
                }
            }
        } else if (command.matches(patternDelete) || command.matches(patternUpdate)) {
            commandNeedId = true;
            return true;
        }
        return false;
    }

}
