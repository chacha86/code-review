package com.programmers.devcourse.model;

import org.junit.jupiter.api.Test;

import static com.programmers.devcourse.model.CommandType.*;
import static com.programmers.devcourse.model.CommandType.LIST;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    @Test
    void Board생성자Test() {
        // given
        Board board = new Board();

        // when
        System.out.println("=====Logic Start=====");

        assertThat(board.getElement()).isNotNull();

        System.out.println("=====Logic End=====");
        // then
    }

    @Test
    void 명령정의Test() {
        // given
        Board board = new Board();

        // when
        System.out.println("=====Logic Start=====");

        CommandType register = board.defineCommand("등록");
        CommandType list = board.defineCommand("목록");
        CommandType exception = board.defineCommand("예외");

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(register).isEqualTo(REGISTER),
                () -> assertThat(list).isEqualTo(LIST),
                () -> assertThat(exception).isEqualTo(EXIT));
    }

    @Test
    void 게시판속명언추가테스트() {
        // given
        Board board = new Board();

        // when
        System.out.println("=====Logic Start=====");

        int sayingNumber = board.add(new Saying("author", "content"));

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(board.getElement().size()).isEqualTo(1),
                () -> assertThat(sayingNumber).isEqualTo(1));

    }

    @Test
    void 명언원소조회테스트() {
        // given
        Board board = new Board();

        // when
        System.out.println("=====Logic Start=====");

        System.out.println("=====Logic End=====");
        // then
        assertThat(board.getElement()).isNotNull();

    }

    @Test
    void 게시판에_속한_명언의개수_조회() {
        // given
        Board board = new Board();
        board.add(new Saying("a1", "c1"));
        board.add(new Saying("a2", "c2"));
        // when
        System.out.println("=====Logic Start=====");

        int boardSize = board.getBoardSize();

        System.out.println("=====Logic End=====");
        // then
        assertThat(boardSize).isEqualTo(2);
    }

    @Test
    void 특정인덱스번호의_속담조회() {
        // given
        Board board = new Board();
        Saying saying1 = new Saying("a1", "c1");
        Saying saying2 = new Saying("a2", "c2");
        int sayingNumber1 = board.add(saying1);
        int sayingNumber2 = board.add(saying2);
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
        assertAll(
                () -> assertThat(board.getElement(sayingNumber2 - 1)).isEqualTo(saying2),
                ()->assertThat(board.getElement(sayingNumber1 - 1)).isEqualTo(saying1));
    }

    @Test
    void 속돔조회시_인덱스범위를_넘었을때() {
        // given
        Board board = new Board();
        Saying saying1 = new Saying("a1", "c1");
        Saying saying2 = new Saying("a2", "c2");
        final int OVER_IDX = 2;
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then

        assertThrows(IllegalArgumentException.class,()-> assertThat(board.getElement(OVER_IDX)));
    }
}