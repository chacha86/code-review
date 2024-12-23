package com.programmers.devcourse.view;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.Saying;

import java.util.Map;

public class OutputView {
    public void printInit() {
        System.out.println("== 명언 앱 ==");
    }

    public void printRegisterNumber(long registerNumber) {
        System.out.println(registerNumber + "번 명언이 등록되었습니다.");
    }

    public void printOptions() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
    }

    public void printSayingInfo(Board board) {
        Map<Long, Saying> element = board.getElementsMap();
        for (Map.Entry<Long, Saying> entry : element.entrySet()) {
            System.out.println(entry.getKey() + " " + "/" + " " + entry.getValue().getAuthor() + " " + "/" + " " + entry.getValue().getContents());
        }
    }

    public void printRemove(String id) {
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    public void printNotExistSayingNumber(String id) {
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    public void printOriginalSayingContent(String contents) {
        System.out.println("명언(기존) : " + contents);
    }

    public void printOriginalSayingAuthor(String author) {
        System.out.println("작가(기존) : " + author);

    }

    public void printBuildSuccess() {
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
