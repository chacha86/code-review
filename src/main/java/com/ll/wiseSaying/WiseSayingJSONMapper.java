package com.ll.wiseSaying;

import java.util.Map;
import java.util.stream.Collectors;

public class WiseSayingJSONMapper {

    public WiseSaying toObject(final String wiseSayingJSON) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.setId(Long.valueOf(extractValue(wiseSayingJSON, "id")));
        wiseSaying.setAuthor(extractValue(wiseSayingJSON, "author"));
        wiseSaying.setContent(extractValue(wiseSayingJSON, "content"));
        return wiseSaying;
    }

    public String toJSON(final WiseSaying wiseSaying) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"id\": ").append(wiseSaying.getId()).append(", ");
        jsonBuilder.append("\"content\": \"").append(wiseSaying.getContent()).append("\", ");
        jsonBuilder.append("\"author\": \"").append(wiseSaying.getAuthor()).append("\"");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public String toJSON(final Map<Long, WiseSaying> wiseSayingMap) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        jsonBuilder.append(wiseSayingMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(this::toJSON)
                .collect(Collectors.joining(", ")));
        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    private String extractValue(final String json, final String key) {
        String searchKey = "\"" + key + "\":";
        int start = json.indexOf(searchKey);
        if (start == -1) {
            return null;
        }
        start = json.indexOf(searchKey) + searchKey.length();

        // string
        if (json.charAt(start) == ' ') {
            start++;
        }
        if (json.charAt(start) == '"') {
            int end = start + 1;
            while (end < json.length() && json.charAt(end) != '"') {
                end++;
            }
            return json.substring(start + 1, end);
        }

        // value
        int end = json.indexOf(",", start);
        if (end == -1) {
            end = json.indexOf("}", start);
        }
        String value = json.substring(start, end).trim();
        return value;
    }
}
