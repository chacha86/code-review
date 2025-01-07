package common.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String mapToJson(Map<String, Object> map) {

        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{\n");

        String str = map.keySet().stream()
                .map(k -> map.get(k) instanceof String
                        ? "    \"%s\" : \"%s\"".formatted(k, map.get(k))
                        : "    \"%s\" : %s".formatted(k, map.get(k))
                ).collect(Collectors.joining(",\n"));

        jsonBuilder.append(str);
        jsonBuilder.append("\n}");

        return jsonBuilder.toString();
    }

    public static void writeAsMap(String filePath, Map<String, Object> wiseSayingMap) {
        String jsonStr = mapToJson(wiseSayingMap);
        FileUtil.write(filePath, jsonStr);
    }

    public static Map<String, Object> readAsMap(String filePath) {
        String jsonStr = FileUtil.readAsString(filePath);
        return jsonToMap(jsonStr);
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {

        Map<String, Object> resultMap = new LinkedHashMap<>();

        jsonStr = jsonStr.replaceAll("\\{", "")
                .replaceAll("}", "")
                .replaceAll("\n", "")
                .replaceAll(" : ", ":");

        Arrays.stream(jsonStr.split(","))
                .map(p -> p.trim().split(":"))
                .forEach(p -> {
                    String key = p[0].replaceAll("\"", "");
                    String value = p[1];

                    if (value.startsWith("\"")) {
                        resultMap.put(key, value.replaceAll("\"", ""));
                    } else if (value.contains(".")) {
                        resultMap.put(key, Double.parseDouble(value));
                    } else if (value.equals("true") || value.equals("false")) {
                        resultMap.put(key, Boolean.parseBoolean(value));
                    } else {
                        resultMap.put(key, Integer.parseInt(value));
                    }
                });

        return resultMap;
    }
}