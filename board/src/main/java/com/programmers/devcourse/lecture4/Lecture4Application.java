package com.programmers.devcourse.lecture4;

import com.programmers.devcourse.lecture4.controller.SayingController;
import com.programmers.devcourse.lecture4.view.InputView;
import com.programmers.devcourse.lecture4.view.OutputView;

import java.io.IOException;

public class Lecture4Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        SayingController sayingController = new SayingController(inputView,outputView);
        sayingController.run();
    }
}
