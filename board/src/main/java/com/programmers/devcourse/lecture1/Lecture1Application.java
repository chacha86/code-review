package com.programmers.devcourse.lecture1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lecture1Application {
    public static void main(String[] args) throws IOException {
        System.out.println("== 명언 앱 ==");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("명령) ");
        String input = br.readLine();
        if (!input.equals("종료")) {
            throw new IllegalArgumentException("not allow command");
        }
    }
}
