package com.programmers.devcourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SayingTest {
    @Nested
    @DisplayName("명언객체 필드 조회 테스트")
    class SayingFieldTest {
        @Test
        void getFieldSuccessTest() {
            // given
            String author = "author";

            String contents = "contents";
            Saying saying = new Saying(author, contents);

            // when
            System.out.println("=====Logic Start=====");

            assertAll(() -> assertThat(saying.getAuthor()).isEqualTo(author),
                    () -> assertThat(saying.getContents()).isEqualTo(contents));

            System.out.println("=====Logic End=====");
            // then
        }
    }

}