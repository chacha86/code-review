package com.ll.wisesaying;

import static com.ll.wisesaying.util.InputUtil.*;
import static com.ll.wisesaying.util.OutputUtil.*;

import java.io.IOException;

import com.ll.wisesaying.controller.WiseSayingController;
import com.ll.wisesaying.exception.WiseSayingException;
import com.ll.wisesaying.util.Validator;

public class App {
    private final WiseSayingController wiseSayingController;

    public App(WiseSayingController wiseSayingController) {
        this.wiseSayingController = wiseSayingController;
    }

    public void run() throws IOException {
        printlnMessage(HEADER);

        while (true) {
            String cmd = bf.readLine();

            if (cmd.equals(END))
                return;

            try {
                Validator.validateInput(cmd);
                wiseSayingController.process(cmd);
            } catch (WiseSayingException e) {
                printError(e.getMessage());
            }
        }
    }
}
