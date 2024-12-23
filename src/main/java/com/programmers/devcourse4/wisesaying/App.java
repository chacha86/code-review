package com.programmers.devcourse4.wisesaying;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {

    private final WiseSayingController wiseSayingController = new WiseSayingController();

    public void run() throws IOException, ParseException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String command = "";
        System.out.println("== 명언 앱 ==");
        while(true){

            System.out.print("명령) ");

            //명령어 입력 받기
            command = bufferedReader.readLine();

            if (command.equals("등록") || command.equals("목록") || command.equals("종료")||
                    command.contains("삭제") || command.contains("수정") || command.equals("빌드")) {
                wiseSayingController.mapping(command);
            }
        }


    }
}

