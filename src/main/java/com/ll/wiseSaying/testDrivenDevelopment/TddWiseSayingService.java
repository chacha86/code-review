package com.ll.wiseSaying.testDrivenDevelopment;

public class TddWiseSayingService {

    private final static TddWiseSayingService instance = new TddWiseSayingService();
    private TddWiseSayingService() {}
    public static synchronized TddWiseSayingService getInstance() {
        return instance;
    }
}
