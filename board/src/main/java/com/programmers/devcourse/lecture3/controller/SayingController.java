package com.programmers.devcourse.lecture3.controller;

import com.programmers.devcourse.lecture3.model.Board;
import com.programmers.devcourse.lecture3.model.Saying;
import com.programmers.devcourse.lecture3.view.InputView;
import com.programmers.devcourse.lecture3.view.OutputView;

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
                int boardNumber = board.add(saying);
                outputView.printRegisterNumber(boardNumber);
            }else{
                break;
            }
        }
    }
}
