package com.ll.wiseSaying;

import java.util.Scanner;

public class App {

    static final int INITIAL_SAMPLE_SIZE = 10;

    public void run(Scanner scanner) {
        WiseSayingController controller = new WiseSayingController();
        initSampleData(controller);
        System.out.println("== 명언 앱 ==");
        System.out.println("== 명언 앱 ==");
        controller.handleCommand(scanner);
    }

    private void initSampleData(WiseSayingController controller) {
        for(int i = 1; i <= INITIAL_SAMPLE_SIZE; i++) {
            controller.addSampleData("명언 " + i, "작자미상 " + i);
        }
    }
}
