package com.programmers.devcourse;

import com.programmers.devcourse.controller.SayingController;
import com.programmers.devcourse.view.InputView;
import com.programmers.devcourse.view.OutputView;

import java.io.IOException;

public class LectureApplication {
    public static void main(String[] args) throws IOException {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        SayingController sayingController = new SayingController(inputView, outputView);
        sayingController.run();
    }
}
