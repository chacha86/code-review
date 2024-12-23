package com.example.wisesaying.controller;

import com.example.wisesaying.service.WiseSayingService;
import com.example.wisesaying.service.impl.WiseSayingServiceImpl;
import org.springframework.stereotype.Controller;

import java.io.IOException;

//@RequiredArgsConstructor
@Controller
public class WiseSayingController{

    private final WiseSayingService wiseSayingService = new WiseSayingServiceImpl();

    // 등록
    public void register() throws IOException {
        wiseSayingService.register();
    }

    public void getList() {
        wiseSayingService.getList();
    }

    public void delete() {
        wiseSayingService.delete();
    }

    public void update() {
        wiseSayingService.update();
    }

    public void toBuild() {
        wiseSayingService.toBuild();
    }
}
