package com.programmers.devcourse.view;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.Saying;

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

    public void printSayingInfo(Saying saying, int sayingNumber) {
        System.out.println(sayingNumber+" "+"/"+" "+saying.getAuthor()+" "+"/"+" "+saying.getContents());
    }
}
