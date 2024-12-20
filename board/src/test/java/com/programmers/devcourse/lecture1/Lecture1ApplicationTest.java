package com.programmers.devcourse.lecture1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class Lecture1ApplicationTest {
    private static ByteArrayOutputStream outputMessage;

    @BeforeEach
    void init() {
        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));
    }

    @AfterEach
    void clear() {
        System.setOut(System.out);
    }

    @DisplayName("종료명령어 입력시 종료테스트")
    @Test
    void commandExitTest() {
        // given
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }
}