package common.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUtilTest {
    static final String TEST_DIRECTORY_PATH = "db/test";

    @BeforeAll
    static void beforeAll() {
        FileUtil.createDir(TEST_DIRECTORY_PATH);
    }

    @AfterAll
    static void afterAll() {
        FileUtil.deleteForce(TEST_DIRECTORY_PATH);
    }

    @Test
    @DisplayName("파일 생성. 내용이 없는 빈 파일 생성")
    void createEmptyFile() {

        String file = TEST_DIRECTORY_PATH+"/test.txt";

        FileUtil.createFile(file);

        assertThat(Files.exists(Paths.get(file)))
                .isTrue();
    }

    @Test
    @DisplayName("파일 내용 읽어오기")
    void loadFile() {

        String file = TEST_DIRECTORY_PATH+"/test.txt";
        String testContent = "Hello, World";

        FileUtil.write(file, testContent);
        String content = FileUtil.readAsString(file);

        assertThat(content)
                .isEqualTo(testContent);
    }

    @Test
    @DisplayName("파일 내용 수정")
    void modifyFile() {

        String file = TEST_DIRECTORY_PATH+"/test.txt";
        String writeContent = "modify content";

        FileUtil.write(file, writeContent);
        String readContent = FileUtil.readAsString(file);

        assertThat(readContent)
                .isEqualTo(writeContent);
    }

    @Test
    @DisplayName("파일 삭제")
    void deleteFile() {

        String file = TEST_DIRECTORY_PATH+"/test.txt";

        // test3.txt 파일 생성
        FileUtil.createFile(file);
        assertThat(Files.exists(Paths.get(file)))
                .isTrue();

        // test3.txt 파일 삭제
        FileUtil.delete(file);

        // test3.txt 존재 여부 확인
        assertThat(Files.exists(Paths.get(file)))
                .isFalse();

    }

    @Test
    @DisplayName("폴더 생성")
    void createFolder() {

        FileUtil.createDir(TEST_DIRECTORY_PATH);
        Path testPath = Paths.get(TEST_DIRECTORY_PATH);

        assertThat(Files.exists(testPath))
                .isTrue();

        assertThat(Files.isDirectory(testPath))
                .isTrue();

    }

    @Test
    @DisplayName("폴더 삭제")
    void deleteFolder() {
        FileUtil.delete(TEST_DIRECTORY_PATH);

        assertThat(Files.exists(Paths.get(TEST_DIRECTORY_PATH)))
                .isFalse();
    }

    @Test
    @DisplayName("파일 생성 -> 없는 폴더에 생성 시도하면 폴더를 생성한 후에 파일 생성")
    void createFileInNewPath() {
        String nestedPath = TEST_DIRECTORY_PATH+"/test2/test.txt";

        FileUtil.createFile(nestedPath);

        boolean rst = Files.exists(Paths.get(nestedPath));
        assertThat(rst)
                .isTrue();
    }

    @Test
    @DisplayName("파일 삭제 -> 폴더가 비어있지 않을 때 안의 내용까지 같이 삭제")
    void deleteForce() {
        String nestedPath = TEST_DIRECTORY_PATH+"/test2/test.txt";

        FileUtil.deleteForce(nestedPath);

        boolean rst = Files.exists(Paths.get(nestedPath));
        assertThat(rst)
                .isFalse();
    }
}
