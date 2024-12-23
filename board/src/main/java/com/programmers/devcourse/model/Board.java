package com.programmers.devcourse.model;

import java.util.*;

public class Board {
    private final Map<Long, Saying> element;
    private Long idx = 1L;

    public Board() {
        this.element = new TreeMap<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o2.compareTo(o1);
            }
        });
    }


    public CommandType defineCommand(String command) {
        if (command.equals("등록")) {
            return CommandType.REGISTER;
        } else if (command.equals("목록")) {
            return CommandType.LIST;
        } else if (command.startsWith("삭제")) {
            return CommandType.REMOVE;
        } else if (command.startsWith("수정")) {
            return CommandType.MODIFY;
        }
        return CommandType.EXIT;
    }

    public int add(Saying saying) {
        element.putIfAbsent(idx++, saying);
        return element.size();
    }

    public Map<Long, Saying> getElementsMap() {
        return this.element;
    }

    public Saying getElement(Long id) {
        return this.element.get(id);
    }

    public int getBoardSize() {
        return element.size();
    }

    public void remove(Long id) {
        element.remove(id);
    }

    public boolean isExist(Long id) {
        if (!element.containsKey(id)) {
            return false;
        }
        return true;
    }


    public void modify(Long id, Saying saying) {
        element.put(id, saying);
    }
}
