package com.ll.wisesaying.controller;

import static com.ll.wisesaying.constant.Constant.*;
import static com.ll.wisesaying.util.InputUtil.*;
import static com.ll.wisesaying.util.OutputUtil.*;

import java.io.IOException;

import com.ll.wisesaying.domain.WiseSaying;
import com.ll.wisesaying.service.WiseSayingService;

public class WiseSayingController {

    private final WiseSayingService wiseSayingService;

    public WiseSayingController(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void process(String cmd) throws IOException {
        if (cmd.equals(ENROLL)) {
            register();
            return;
        }

        if (cmd.equals(LIST)) {
            list();
            return;
        }

        if (cmd.startsWith(DELETE)) {
            delete(cmd);
            return;
        }

        if (cmd.startsWith(EDIT)) {
            edit(cmd);
            return;
        }

        if (cmd.equals(BUILD)) {
            build();
            return;
        }
    }

    public void register() throws IOException {
        printMessage(WISESAYING);
        String content = inputMessage();

        printMessage(AUTHOR);
        String author = inputMessage();

        WiseSaying wiseSaying = wiseSayingService.inputWiseSaying(content, author);
        wiseSayingService.saveFile(wiseSaying);

        printlnMessage(wiseSaying.getId() + ENROLLED);
    }

    public void list() {
        printlnMessage(FORM);
        printlnMessage(TABLE_SEPARATOR);

        printWiseSayings(wiseSayingService.getWiseSayings());
    }

    public void delete(String cmd) throws IOException {
        int idx = wiseSayingService.deleteWiseSaying(cmd);
        wiseSayingService.deleteFile(idx);

        printlnMessage(idx + DELETED);
    }

    public void edit(String cmd) throws IOException {
        WiseSaying wiseSaying = wiseSayingService.getWiseSaying(cmd);

        printlnMessage(EXISTING_WISESAYING + wiseSaying.getContent());
        printMessage(WISESAYING);
        String content = inputMessage();

        printlnMessage(EXISTING_AUTHOR + wiseSaying.getAuthor());
        printMessage(AUTHOR);
        String author = inputMessage();

        WiseSaying newWiseSaying = wiseSayingService.editWiseSaying(wiseSaying.getId(), content, author);
        printlnMessage(WISESAYING + newWiseSaying.getContent());
        printlnMessage(AUTHOR + newWiseSaying.getAuthor());

        wiseSayingService.saveFile(newWiseSaying);
    }

    public void build() throws IOException {
        wiseSayingService.buildPhrase();
        printlnMessage(ALL_FILE + BUILDED);
    }
}
