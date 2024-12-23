package com.programmers.devcourse.controller;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.CommandType;
import com.programmers.devcourse.model.Saying;
import com.programmers.devcourse.view.InputView;
import com.programmers.devcourse.view.OutputView;

import java.io.IOException;

import static com.programmers.devcourse.model.CommandType.*;

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
            CommandType commandType = board.defineCommand(command);
            if (commandType.equals(REGISTER)) {
                Saying saying = inputView.inputSaying();
                int boardNumber = board.add(saying);
                outputView.printRegisterNumber(boardNumber);
            } else if (commandType.equals(LIST)) {
                outputView.printOptions();
                outputView.printSayingInfo(board);
            } else if (commandType.equals(REMOVE)) {
                String id = command.substring(6);
                board.remove(Long.parseLong(id));
                outputView.printRemove(id);
            } else {
                break;
            }
        }
    }
}
