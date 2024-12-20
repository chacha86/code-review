package com.ll.wisesaying;

import java.io.IOException;

import com.ll.wisesaying.controller.WiseSayingController;
import com.ll.wisesaying.repository.WiseSayingRepository;
import com.ll.wisesaying.service.WiseSayingService;

public class Main {

    public static void main(String[] args) throws IOException {
        new App(
            new WiseSayingController(new WiseSayingService(new WiseSayingRepository()))).run();
    }
}
