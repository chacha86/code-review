package com.programmers.devcourse.lecture4.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Saying> element;

    public Board() {
        this.element = new ArrayList<Saying>();
    }


    public boolean isRegister(String command) {
        if (command.equals("등록")) {
            return true;
        }
        return false;
    }

    public int add(Saying saying) {
        element.add(saying);
        return element.size();
    }
}
