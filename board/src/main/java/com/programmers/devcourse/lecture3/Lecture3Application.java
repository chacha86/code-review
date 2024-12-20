package com.programmers.devcourse.lecture3;

import com.programmers.devcourse.lecture3.controller.SayingController;
import com.programmers.devcourse.lecture3.view.InputView;
import com.programmers.devcourse.lecture3.view.OutputView;

import java.io.IOException;

public class Lecture3Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        SayingController sayingController = new SayingController(inputView,outputView);
        sayingController.run();
    }
}
