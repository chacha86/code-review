package com.programmers.devcourse.view;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.Saying;

import java.util.Map;

public class OutputView {
    public void printInit() {
        System.out.println("== 명언 앱 ==");
    }

    public void printRegisterNumber(int registerNumber) {
        System.out.println(registerNumber + "번 명언이 등록되었습니다.");
    }

    public void printOptions() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
    }

    public void printSayingInfo(Board board) {
        Map<Long, Saying> element = board.getElement();
        for (Map.Entry<Long, Saying> entry : element.entrySet()) {
            System.out.println(entry.getKey() + " " + "/" + " " + entry.getValue().getAuthor() + " " + "/" + " " + entry.getValue().getContents());
        }
    }

    public void printRemove(String id) {
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }
}
