package com.programmers.devcourse.controller;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.CommandType;
import com.programmers.devcourse.model.Saying;
import com.programmers.devcourse.view.InputView;
import com.programmers.devcourse.view.JsonDBInputView;
import com.programmers.devcourse.view.JsonDBOutputView;
import com.programmers.devcourse.view.OutputView;

import java.io.IOException;
import java.util.Map;

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
        Map<Long, Saying> savedSayingMap = JsonDBInputView.readSaying();
        Long lastId = JsonDBInputView.readLastId();
        Board board = new Board(savedSayingMap, lastId);
        while (true) {
            String command = inputView.command();
            CommandType commandType = board.defineCommand(command);
            if (commandType.equals(REGISTER)) {
                Saying saying = inputView.inputSaying();
                long boardNumber = board.add(saying);
                outputView.printRegisterNumber(boardNumber);
            } else if (commandType.equals(LIST)) {
                outputView.printOptions();
                outputView.printSayingInfo(board);
            } else if (commandType.equals(REMOVE)) {
                String id = command.substring(6);
                if (!board.isExist(Long.valueOf(id))) {
                    outputView.printNotExistSayingNumber(id);
                    continue;
                }
                board.remove(Long.parseLong(id));
                outputView.printRemove(id);

            } else if (commandType.equals(MODIFY)) {
                String id = command.substring(6);
                if (!board.isExist(Long.valueOf(id))) {
                    outputView.printNotExistSayingNumber(id);
                    continue;
                }
                Saying element = board.getElement(Long.parseLong(id));

                outputView.printOriginalSayingContent(element.getContents());
                String newContent = inputView.inputNewContent();
                outputView.printOriginalSayingAuthor(element.getAuthor());
                String newAuthor = inputView.inputNewAuthor();

                Saying modifySaying = element.modify(newAuthor, newContent);
                board.modify(Long.parseLong(id), modifySaying);
            } else if (commandType.equals(BUILD)) {
                JsonDBOutputView.saveSaying(board);
                outputView.printBuildSuccess();
            } else {
                JsonDBOutputView.lastSayingNumber(board.getLastBoarNumber());
                break;
            }
        }
    }
}
