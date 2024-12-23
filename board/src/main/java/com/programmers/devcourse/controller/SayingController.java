package com.programmers.devcourse.controller;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.Saying;
import com.programmers.devcourse.view.InputView;
import com.programmers.devcourse.view.JsonDBInputView;
import com.programmers.devcourse.view.OutputView;

import java.io.IOException;
import java.util.Map;

public class SayingController {
    private final InputView inputView;
    private final OutputView outputView;

    public SayingController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() throws IOException {
        outputView.printInit();
        Map<Long, Saying> savedSayingMap = JsonDBInputView.readSaying();
        Long lastId = JsonDBInputView.readLastId();
        SayingCommander sayingCommander = new SayingCommander(inputView, outputView);
        Board board = new Board(savedSayingMap, lastId);
        while (true) {
            boolean isContinue = sayingCommander.command(board);
            if (!isContinue) {
                break;
            }
        }
    }
}
