package com.programmers.devcourse.generator;

public class SayingIdxGenerator {
    private static SayingIdxGenerator instance = new SayingIdxGenerator();
    private Long idx = 0L;

    private SayingIdxGenerator() {

    }

    public static SayingIdxGenerator getInstance() {
        return instance;
    }

    public Long getIdx() {
        return idx++;
    }
}
