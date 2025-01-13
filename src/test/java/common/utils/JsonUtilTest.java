package common.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiseSaying.WiseSaying;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilTest {

    @BeforeAll
    static void beforeAll() {
        FileUtil.createDir("db/test");
    }

    @AfterAll
    static void afterAll() {
        FileUtil.deleteForce("db/test");
    }

    @Test
    @DisplayName("Map을 Json으로 변환1 - 속성이 1개")
    void MapToJson() {

        Map<String, Object> map = Map.of("name", "홍길동");

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 Json으로 변환2 - 속성이 2개")
    void MapToJsonTwoItems() {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "홍길동");
        map.put("home", "서울");

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동",
                            "home" : "서울"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 Json으로 변환3 - 속성이 3개, 문자와 숫자 혼합")
    void MapToJsonThreeItems() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", "홍길동");
        map.put("home", "서울");
        map.put("age", 20);

        String jsonStr = JsonUtil.mapToJson(map);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "name" : "홍길동",
                            "home" : "서울",
                            "age" : 20
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("WiseSaying을 Map으로 변환 -> Json으로 변환")
    void WiseSayingToJson() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        Map<String, Object> wiseSayingMap = wiseSaying.toMap();

        String jsonStr = JsonUtil.mapToJson(wiseSayingMap);

        assertThat(jsonStr)
                .isEqualTo("""
                        {
                            "id" : 1,
                            "content" : "aaa",
                            "author" : "bbb"
                        }
                        """.stripIndent().trim());
    }

    @Test
    @DisplayName("Map을 넘기면 Json 파일로 저장하기")
    void MapToJsonFile() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        Map<String, Object> wiseSayingMap = wiseSaying.toMap();

        String filePath = "db/test/%d.json".formatted(wiseSaying.getId());
        JsonUtil.writeAsMap(filePath, wiseSayingMap);

        boolean rst = Files.exists(Path.of(filePath));
        assertThat(rst).isTrue();

        String content = FileUtil.readAsString(filePath);
        assertThat(content)
                .isEqualTo("""
                        {
                            "id" : 1,
                            "content" : "aaa",
                            "author" : "bbb"
                        }
                        """.stripIndent().trim()
                );
    }

    @Test
    @DisplayName("Json 문자열을 Map으로 변환하기")
    void JsonToMap() {

        String jsonStr = """
                {
                    "id" : 1,
                    "content" : "aaa",
                    "author" : "bbb"
                }
                """;

        Map<String, Object> map = JsonUtil.jsonToMap(jsonStr);

        assertThat(map)
                .hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("content", "aaa")
                .containsEntry("author", "bbb");

    }

    @Test
    @DisplayName("파일명을 넘기면 Map으로 읽어오기")
    void getFileToMap() {

        WiseSaying wiseSaying = new WiseSaying(1, "aaa", "bbb");
        Map<String, Object> wiseSayingMap = wiseSaying.toMap();

        String filePath = "db/test/%d.json".formatted(wiseSaying.getId());
        JsonUtil.writeAsMap(filePath, wiseSayingMap);

        Map<String, Object> map = JsonUtil.readAsMap(filePath);

        assertThat(map)
                .hasSize(3)
                .containsEntry("id", 1)
                .containsEntry("content", "aaa")
                .containsEntry("author", "bbb");
    }

    @Test
    @DisplayName("Map을 WiseSaying으로 변환")
    void mapToWiseSaying() {

        String filePath = "db/test/%d.json".formatted(1);
        Map<String, Object> map = JsonUtil.readAsMap(filePath);

        WiseSaying wiseSaying = WiseSaying.fromMap(map);

        assertThat(wiseSaying.getId()).isEqualTo(1);
        assertThat(wiseSaying.getContent()).isEqualTo("aaa");
        assertThat(wiseSaying.getAuthor()).isEqualTo("bbb");

    }
}