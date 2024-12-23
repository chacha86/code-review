package com.ll.wiseSaying;

import java.util.HashMap;
import java.util.Map;

public class Attribute {
    private Map<String, String> attributeMap;

    public Attribute() {
        attributeMap = new HashMap<>();
    }

    public Attribute add(String name, String value) {
        attributeMap.put(name, value);
        return this;
    }

    public String get(final String name) {
        return attributeMap.get(name);
    }

    public boolean has(final String name) {
        return attributeMap.containsKey(name);
    }
}
