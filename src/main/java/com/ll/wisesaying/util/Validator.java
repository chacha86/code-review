package com.ll.wisesaying.util;

import static com.ll.wisesaying.util.InputUtil.*;
import static com.ll.wisesaying.util.OutputUtil.*;

import com.ll.wisesaying.exception.InputException;

public class Validator {
    public static void validateInput(String cmd) {
        if (!(cmd.contains(ENROLL)
            || cmd.contains(LIST)
            || cmd.contains(DELETE)
            || cmd.contains(EDIT)
            || cmd.contains(BUILD))) {
            throw new InputException(ILLEGAL_INPUT);
        }
    }

    public static void validateCmd(String[] tokens) {
        if (tokens.length <= 1) {
            throw new InputException(ILLEGAL_CMD);
        }
    }

    public static void validatePage(String[] tokens) {
        if (tokens.length == 2 || tokens.length == 4) {
            String lastToken = tokens[tokens.length - 1];
            if (lastToken.chars().anyMatch(c -> !Character.isDigit(c))) {
                throw new InputException(ILLEGAL_CMD);
            }
        }
    }

}
