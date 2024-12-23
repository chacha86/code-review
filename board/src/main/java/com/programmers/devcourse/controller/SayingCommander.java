package com.programmers.devcourse.controller;

import com.programmers.devcourse.model.Board;
import com.programmers.devcourse.model.CommandType;
import com.programmers.devcourse.model.Saying;
import com.programmers.devcourse.view.InputView;
import com.programmers.devcourse.view.JsonDBOutputView;
import com.programmers.devcourse.view.OutputView;

import java.io.IOException;

import static com.programmers.devcourse.model.CommandType.*;

public class SayingCommander {
    private final InputView inputView;
    private final OutputView outputView;

    public SayingCommander(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public boolean command(Board board) throws IOException {
        String command = inputView.command();
        CommandType commandType = CommandType.defineCommand(command);
        if (commandType.equals(REGISTER)) {
            Saying saying = inputView.inputSaying();
            long boardNumber = board.add(saying);
            outputView.printRegisterNumber(boardNumber);
            return true;
        } else if (commandType.equals(LIST)) {
            outputView.printOptions();
            outputView.printSayingInfo(board);
            return true;
        } else if (commandType.equals(REMOVE)) {
            String id = command.substring(6);
            if (!board.isExist(Long.valueOf(id))) {
                outputView.printNotExistSayingNumber(id);
                return true;
            }
            board.remove(Long.parseLong(id));
            outputView.printRemove(id);
            return true;
        } else if (commandType.equals(MODIFY)) {
            String id = command.substring(6);
            if (!board.isExist(Long.valueOf(id))) {
                outputView.printNotExistSayingNumber(id);
                return true;
            }
            Saying element = board.getElement(Long.parseLong(id));
            outputView.printOriginalSayingContent(element.getContents());
            String newContent = inputView.inputNewContent();
            outputView.printOriginalSayingAuthor(element.getAuthor());
            String newAuthor = inputView.inputNewAuthor();
            Saying modifySaying = element.modify(newAuthor, newContent);
            board.modify(Long.parseLong(id), modifySaying);
            return true;
        } else if (commandType.equals(BUILD)) {
            JsonDBOutputView.saveSaying(board);
            outputView.printBuildSuccess();
            JsonDBOutputView.lastSayingNumber(board.getLastBoarNumber());
            return true;
        }
        return false;
    }
}
