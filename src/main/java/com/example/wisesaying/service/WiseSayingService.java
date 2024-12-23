package com.example.wisesaying.service;

import java.io.IOException;

public interface WiseSayingService {

    void register() throws IOException;

    void getList();

    void delete();

    void update();

    void toBuild();
}
