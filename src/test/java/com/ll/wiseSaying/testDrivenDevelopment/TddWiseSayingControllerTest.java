package com.ll.wiseSaying.testDrivenDevelopment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

public class TddWiseSayingControllerTest {

    private ByteArrayOutputStream outContent;
    private TddWiseSayingController controller;

    @BeforeEach
    void beforeEach() {
        AppTest.clear();
        controller = TddWiseSayingController.getInstance();
        outContent = com.ll.wiseSaying.basicDevelopment.TestUtil.setOutToByteArray();
    }

    @AfterEach
    void afterEach() {
        TestUtil.clearSetOutToByteArray(outContent);
    }

    @Test
    @DisplayName("등록")
    void testRegister() {

    }
}
