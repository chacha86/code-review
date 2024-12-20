package com.programmers.devcourse.lecture2.contorller;


import com.programmers.devcourse.lecture2.model.Board;
import com.programmers.devcourse.lecture2.model.Saying;
import com.programmers.devcourse.lecture2.view.InputView;
import com.programmers.devcourse.lecture2.view.OutputView;

import java.io.IOException;

public class SayingController {
    private final InputView inputView;
    private final OutputView outputView;

    public SayingController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        outputView.printInit();
        while (true) {
            String command = inputView.command();
            Board board = new Board();
            if (board.isRegister(command)) {
                Saying saying = inputView.inputSaying();
                board.add(saying);
            }else{
                break;
            }
        }
    }
}
