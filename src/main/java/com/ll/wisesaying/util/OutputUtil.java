package com.ll.wisesaying.util;

import static com.ll.wisesaying.constant.Constant.*;

import java.util.List;

import com.ll.wisesaying.domain.WiseSaying;

public class OutputUtil {

    public static final String HEADER = "== 명언 앱 ==";
    public static final String FORM = "번호 / 작가 / 명언";
    public static final String TABLE_SEPARATOR = "----------------------";
    public static final String WISESAYING = "명언 : ";
    public static final String EXISTING_WISESAYING = "명언(기존) : ";
    public static final String AUTHOR = "작가 : ";
    public static final String EXISTING_AUTHOR = "작가(기존) : ";
    public static final String ENROLLED = "번 명언이 등록되었습니다.";
    public static final String DELETED = "번 명언이 삭제되었습니다.";
    public static final String BUILDED = "파일의 내용이 갱신되었습니다.";
    public static final String NOT_EXIST = "번 명언은 존재하지 않습니다.";
    public static final String ILLEGAL_INPUT = "등록, 목록, 삭제, 수정, 빌드 중에 입력해주세요.";
    public static final String ILLEGAL_CMD = "파라미터 형식으로 작성해주세요.";

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printMessage(String message) {
        System.out.print(message);
    }

    public static void printlnMessage(String message) {
        System.out.println(message);
    }

    public static void printWiseSayings(List<WiseSaying> wiseSayings) {
        wiseSayings.forEach((wiseSaying) -> {
            System.out.println(wiseSaying.getId()
                + SEPARATOR + wiseSaying.getContent()
                + SEPARATOR + wiseSaying.getAuthor());
        });
    }
}
