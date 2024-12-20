package com.ll.wiseSaying.testDrivenDevelopment;

public class TddWiseSayingRepository {

    private final static TddWiseSayingRepository instance = new TddWiseSayingRepository();
    private TddWiseSayingRepository() {
    }
    public static synchronized TddWiseSayingRepository getInstance() {
        return instance;
    }
}
