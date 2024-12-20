package TDD.test.Controller;

import TDD.main.controller.ProverbController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProverbControllerTest {

    private ProverbController controller;

    @BeforeEach
    void setUp() {
        // 테스트 전에 ProverbController 객체 초기화
        this.controller = new ProverbController();
    }


    @Test
    @DisplayName("명언 등록 테스트")
    void testRegisterProverb() {
        String result = controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        assertTrue(result.contains("1번 명언이 등록되었습니다."));
    }

    @Test
    @DisplayName("명언 목록 조회 테스트")
    void testListProverbs() {
        controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        String result = controller.listProverbs(1);
        assertTrue(result.contains("1 / 박종현 / 오늘도 열리지 않는 문을 두드린다."));
    }

    @Test
    @DisplayName("명언 검색 테스트")
    void testSearchProverb() {
        controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        String result = controller.searchProverb("author", "박종현");
        assertTrue(result.contains("1 / 박종현 / 오늘도 열리지 않는 문을 두드린다."));
    }

    @Test
    @DisplayName("명언 삭제 테스트")
    void testDeleteProverb() {
        controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        String result = controller.deleteProverb(1);
        assertEquals("1번 명언이 삭제되었습니다.", result);
    }

    @Test
    @DisplayName("명언 수정 테스트")
    void testUpdateProverb() {
        controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        String result = controller.updateProverb(1, "문이 열렸다.", "성공한 박종현");
        assertEquals("1번 명언이 수정되었습니다.", result);
    }

    @Test
    @DisplayName("데이터 파일 저장 테스트")
    void testSaveDataFile() {
        controller.registerProverb("오늘도 열리지 않는 문을 두드린다.", "박종현");
        String result = controller.saveDataToFile();
        assertEquals("data.json 파일이 생성되었습니다.", result);
    }

}
