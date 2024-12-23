package com.example.wisesaying;

import com.example.wisesaying.controller.WiseSayingController;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public class App {
    private final WiseSayingController controller = new WiseSayingController();
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void start() throws IOException {
        String command = "";

        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.println("명령) ");

            command = br.readLine();
            if (command.equals("등록")) {
                controller.register();
            } else if (command.equals("목록")) {
                controller.getList();
            } else if (command.equals("삭제")) {
                controller.delete();
            } else if (command.equals("수정")) {
                controller.update();
            } else if (command.equals("빌드")) {
                controller.toBuild();
            } else if (command.equals("종료")) {
                break;
            }

        }
        System.out.println("명령) 종료");
    }
}
