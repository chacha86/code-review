import org.example.wiseSaying.WiseSayingController;
import org.example.wiseSaying.WiseSayingRepository;
import org.example.wiseSaying.WiseSayingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    private WiseSayingRepository wiseSayingRepository;
    private WiseSayingService wiseSayingService;
    private WiseSayingController wiseSayingController;

    @BeforeEach
    void beforeEach() {
        wiseSayingRepository = new WiseSayingRepository();
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
        wiseSayingController = new WiseSayingController(wiseSayingService);
    }

    @Test
    @DisplayName("명언 등록")
    void createWiseSaying() throws IOException {
        final String input = ("""
                현재를 사랑하라.
                작자미상
                """);
        ByteArrayInputStream inputStream = TestUtil.getInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        Path dbPath = Path.of("db/wiseSaying");
        if (!Files.exists(dbPath)) {
            Files.createDirectories(dbPath);
        }

        wiseSayingController.createWiseSaying(br);
        TestUtil.clearSetOutToByteArray(outputStream);

        Path filePath = Path.of(dbPath.toString(), "1.json");
        assertThat(Files.exists(filePath)).isTrue();

        String content = Files.readString(filePath);

        assertThat(content)
                .contains("현재를 사랑하라.", "작자미상");
    }

    @Test
    @DisplayName("명언 조회")
    void printWiseSaying() throws IOException {
        String input = """
            현재를 사랑하라.
            작자미상
            """;
        ByteArrayInputStream inputStream = TestUtil.getInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        wiseSayingController.createWiseSaying(br);
        TestUtil.clearSetOutToByteArray(outputStream);

        outputStream = TestUtil.setOutToByteArray();
        wiseSayingController.printWiseSayings("목록?page=1");
        TestUtil.clearSetOutToByteArray(outputStream);

        String output = outputStream.toString().trim();

        assertThat(output).contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("명언 수정")
    void updateWiseSaying() throws IOException {
        String input = """
                현재를 사랑하라.
                작자미상
                미래를 꿈꾸다.
                작자미상
                """;
        ByteArrayInputStream inputStream = TestUtil.getInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        wiseSayingController.createWiseSaying(br);
        TestUtil.clearSetOutToByteArray(outputStream);

        Path dbPath = Path.of("db/wiseSaying");
        if (!Files.exists(dbPath)) {
            Files.createDirectories(dbPath);
        }

        Path filePath = Path.of(dbPath.toString(), "1.json");
        assertThat(Files.exists(filePath)).isTrue();

        wiseSayingController.updateWiseSayingById(1, br);
        TestUtil.clearSetOutToByteArray(outputStream);

        String fileContent = Files.readString(filePath);

        assertThat(fileContent)
                .contains("미래를 꿈꾸다.")
                .contains("작자미상");
    }

    @Test
    @DisplayName("명언 삭제")
    void deleteWiseSaying() throws IOException {
        String input = """
                현재를 사랑하라.
                작자미상
                """;
        ByteArrayInputStream inputStream = TestUtil.getInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        wiseSayingController.createWiseSaying(br);
        TestUtil.clearSetOutToByteArray(outputStream);

        Path dbPath = Path.of("db/wiseSaying");
        if (!Files.exists(dbPath)) {
            Files.createDirectories(dbPath);
        }

        Path filePath = Path.of(dbPath.toString(), "1.json");
        assertThat(Files.exists(filePath)).isTrue();

        TestUtil.clearSetOutToByteArray(outputStream);
        wiseSayingController.deleteWiseSayingById(1);

        assertThat(filePath).doesNotExist();
    }

    @Test
    @DisplayName("명언 빌드")
    void buildWiseSaying() throws IOException {
        String input = """
                현재를 사랑하라.
                작자미상
                """;
        ByteArrayInputStream inputStream = TestUtil.getInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        wiseSayingController.createWiseSaying(br);
        TestUtil.clearSetOutToByteArray(outputStream);

        Path dbPath = Path.of("db/wiseSaying");
        if (!Files.exists(dbPath)) {
            Files.createDirectories(dbPath);
        }

        Path filePath = Path.of(dbPath.toString(), "data.json");
        assertThat(Files.exists(filePath)).isTrue();

        TestUtil.clearSetOutToByteArray(outputStream);
        wiseSayingController.buildWiseSaying();

        String fileContent = Files.readString(filePath);

        assertThat(filePath).exists();

        assertThat(fileContent)
                .contains("[{\"author\":\"작자미상\",\"id\":1,\"content\":\"현재를 사랑하라.\"}]");
    }
}
