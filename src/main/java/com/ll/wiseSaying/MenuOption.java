package com.ll.wiseSaying;

import java.util.Arrays;

public enum MenuOption {
    ADD("등록"),
    LIST("목록"),
    UPDATE("수정"),
    DELETE("삭제"),
    BUILD("빌드"),
    EXIT("종료");

    private final String command;

    MenuOption(String command) {
        this.command = command;
    }

    static MenuOption findOptionByCommand(final String command) {
        return Arrays.stream(MenuOption.values())
                .filter(option -> option.command.equals(command))
                .findAny()
                .orElseThrow(() -> new IllegalStateException());
    }

    public String getCommand() {
        return this.command;
    }
}
