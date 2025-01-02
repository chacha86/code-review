package com.ll.wisesaying.controller;

import com.ll.wisesaying.service.WiseSayingService;
import com.ll.wisesaying.repository.WiseSayingRepository;
import com.ll.wisesaying.util.InputUtil;
import com.ll.wisesaying.util.TestUtil;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static com.ll.wisesaying.constant.Constant.*;
import static com.ll.wisesaying.util.OutputUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    private WiseSayingRepository wiseSayingRepository;
    private WiseSayingService wiseSayingService;
    private WiseSayingController controller;
    private ByteArrayOutputStream output;
    private final Path path = Paths.get(DB_FOLDER);

    @BeforeEach
    void setup() throws IOException {
        wiseSayingRepository = new WiseSayingRepository();
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
        controller = new WiseSayingController(wiseSayingService);

        output = TestUtil.setOutToByteArray();
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(file -> {
                if (!file.delete()) {
                    System.err.println("파일 삭제 실패");
                }
            });
        Files.deleteIfExists(path.getParent());

        System.setIn(System.in);
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    void registerTest() throws IOException {
        // Given
        String input = """
                현재를 사랑하라.
                작자미상
                """;
        System.setIn(TestUtil.getInputStream(input));
        InputUtil.resetBufferedReader();

        // When
        controller.register();

        // Then
        Path savedFilePath = Path.of("db/wiseSaying/1.json");
        assertThat(Files.exists(savedFilePath)).isTrue();

        String savedContent = Files.readString(savedFilePath);
        assertThat(savedContent).contains("현재를 사랑하라.", "작자미상");
    }

    @Test
    void deleteTest() throws IOException {
        // Given
        controller.register();

        // When
        controller.delete("삭제?id=1");

        // Then
        Path savedFilePath = Path.of("db/wiseSaying/1.json");
        assertThat(Files.exists(savedFilePath)).isFalse();
    }

    @Test
    void editTest() throws IOException {
        // Given
        String input = """
            현재를 사랑하라.
            작자미상
            수정된 명언
            수정된 작가
            """;
        System.setIn(TestUtil.getInputStream(input));
        InputUtil.resetBufferedReader();

        controller.register();

        // When
        controller.edit("수정?id=1");

        // Then
        Path savedFilePath = Path.of("db/wiseSaying/1.json");
        assertThat(Files.exists(savedFilePath)).isTrue();

        String savedContent = Files.readString(savedFilePath);
        assertThat(savedContent).contains("수정된 명언", "수정된 작가");
    }

    @Test
    void buildTest() throws IOException {
        // Given
        String input = """
                현재를 사랑하라.
                작자미상
                """;
        System.setIn(TestUtil.getInputStream(input));
        InputUtil.resetBufferedReader();

        controller.register();

        // When
        controller.build();

        // Then
        Path allFilePath = Path.of("db/wiseSaying/data.json");
        assertThat(Files.exists(allFilePath)).isTrue();

        String allContent = Files.readString(allFilePath);
        assertThat(allContent).contains("현재를 사랑하라.", "작자미상");
    }
}
