package TDD.test.Service;

import TDD.main.service.ProverbService;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProverbServiceTest {

    @Test
    void testAddProverb() {
        ProverbService service = new ProverbService();
        String result = service.addProverb("현재를 사랑하라.", "작자미상");
        assertEquals("1번 명언이 등록되었습니다.", result);
    }
}
