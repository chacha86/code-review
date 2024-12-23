package com.programmers.devcourse.model;

public enum CommandType {
    REGISTER,EXIT,LIST, MODIFY, BUILD, REMOVE;

    public static CommandType defineCommand(String command) {
        if (command.equals("등록")) {
            return CommandType.REGISTER;
        } else if (command.equals("목록")) {
            return CommandType.LIST;
        } else if (command.startsWith("삭제")) {
            return CommandType.REMOVE;
        } else if (command.startsWith("수정")) {
            return CommandType.MODIFY;
        }else if(command.equals("빌드")){
            return CommandType.BUILD;
        }
        return CommandType.EXIT;
    }
}
