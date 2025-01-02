package com.ll.wisesaying;

import static com.ll.wisesaying.util.InputUtil.*;
import static com.ll.wisesaying.util.OutputUtil.*;
import static com.ll.wisesaying.util.Validator.*;

import java.io.IOException;

import com.ll.wisesaying.controller.WiseSayingController;
import com.ll.wisesaying.exception.WiseSayingException;

public class App {
    private final WiseSayingController wiseSayingController;

    public App(WiseSayingController wiseSayingController) {
        this.wiseSayingController = wiseSayingController;
    }

    public void run() throws IOException {
        printlnMessage(HEADER);

        while (true) {
            String cmd = inputMessage();

            if (cmd.equals(END))
                return;

            try {
                validateInput(cmd);
                wiseSayingController.process(cmd);
            } catch (WiseSayingException e) {
                printError(e.getMessage());
            }
        }
    }
}
