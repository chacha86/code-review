package com.programmers.devcourse.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SayingIdxGeneratorTest {
    @Test
    void generateSayingIdx() {
        // given
        SayingIdxGenerator instance = SayingIdxGenerator.getInstance();

        // when
        System.out.println("=====Logic Start=====");

        Long idx1 = instance.getIdx();
        Long idx2 = instance.getIdx();
        Long idx3 = instance.getIdx();

        System.out.println("=====Logic End=====");
        // then
        assertAll(()-> assertThat(idx1).isEqualTo(0),
                ()-> assertThat(idx2).isEqualTo(1),
                ()-> assertThat(idx3).isEqualTo(2));
    }
}