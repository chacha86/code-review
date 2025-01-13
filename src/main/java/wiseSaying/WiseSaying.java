package wiseSaying;

import common.utils.JsonUtil;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public boolean isNew() {
        return this.id == 0;
    }

    @Override
    public String toString() {
        return "%d / %s / %s".formatted(id, author, content);
    }

//    public String toJson() {
//        Map<String, Object> map = new LinkedHashMap<>(Map.of(
//            "id", id,
//            "content", content,
//            "author", author
//        ));
//        return JsonUtil.mapToJson(map);
//    }

//    public static WiseSaying fromJson(String json) {
//        String[] jsonItems = json
//                .replaceAll("\\{", "")
//                .replaceAll("}", "")
//                .replaceAll("\"", "")
//                .replaceAll("\n", "")
//                .replaceAll("\t", "")
//                .split(",");
//        int id = Integer.parseInt(jsonItems[0].split(":")[1].trim());
//        String content = jsonItems[1].split(":")[1].trim();
//        String author = jsonItems[2].split(":")[1].trim();
//        return new WiseSaying(id, content, author);
//    }

    public Map<String, Object> toMap() {
        return new LinkedHashMap<>(Map.of(
            "id", id,
            "content", content,
            "author", author
        ));
    }

    public static WiseSaying fromMap(Map<String, Object> map) {
        int id = (int) map.get("id");
        String content = (String) map.get("content");
        String author = (String) map.get("author");

        return new WiseSaying(id, content, author);
    }
}
