package com.programmers.devcourse.lecture4.controller;

import com.programmers.devcourse.lecture4.model.Board;
import com.programmers.devcourse.lecture4.model.Saying;
import com.programmers.devcourse.lecture4.view.InputView;
import com.programmers.devcourse.lecture4.view.OutputView;

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
        Board board = new Board();
        while (true) {
            String command = inputView.command();
            if (board.isRegister(command)) {
                Saying saying = inputView.inputSaying();
                int boardNumber = board.add(saying);
                outputView.printRegisterNumber(boardNumber);
            }else{
                break;
            }
        }
    }
}
