package com.programmers.devcourse.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Saying> element;

    public Board() {
        this.element = new ArrayList<Saying>();
    }


    public CommandType defineCommand(String command) {
        if (command.equals("등록")) {
            return CommandType.REGISTER;
        } else if (command.equals("목록")) {
            return CommandType.LIST;
        }
        return CommandType.EXIT;
    }

    public int add(Saying saying) {
        element.add(saying);
        return element.size();
    }

    public List<Saying>getElement(){
        return this.element;
    }

    public int getBoardSize(){
        return element.size();
    }

    public Saying getElement(int index){
        if(getBoardSize()-1<index){
            throw new IllegalArgumentException("인덱스범위를 넘었습니다.");
        }
        return element.get(index);
    }
}
