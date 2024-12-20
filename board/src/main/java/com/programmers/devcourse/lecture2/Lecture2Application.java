package com.programmers.devcourse.lecture2;


import com.programmers.devcourse.lecture2.contorller.SayingController;
import com.programmers.devcourse.lecture2.view.InputView;
import com.programmers.devcourse.lecture2.view.OutputView;

import java.io.IOException;

public class Lecture2Application {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        SayingController sayingController = new SayingController(inputView,outputView);
        sayingController.run();
    }
}
