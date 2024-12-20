package com.ll.wiseSaying.testDrivenDevelopment;

import java.util.Scanner;

public class TddWiseSayingController {

    private final static TddWiseSayingController instance = new TddWiseSayingController();

    private TddWiseSayingController() {
    }

    public static synchronized TddWiseSayingController getInstance() {
        return instance;
    }

    public void register(Scanner scanner) {
    }

    public void search(Scanner scanner, String command) {

    }

    public void modify(Scanner scanner, String command) {

    }

    public void delete(String command) {

    }

    public void build() {

    }
}
