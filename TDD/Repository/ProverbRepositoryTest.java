package TDD.Repository;

import TDD.main.ProverbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProverbRepositoryTest {

    private ProverbRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProverbRepository();
    }

    @Test
    @DisplayName("마지막 ID 읽기 테스트")
    void testReadLastId() {
        int lastId = repository.readLastId();
        assertEquals(1, lastId); // 초기값 1을 기대
    }

    @Test
    @DisplayName("마지막 ID 저장 테스트")
    void testSaveLastId() {
        repository.saveLastId(5);
        int lastId = repository.readLastId();
        assertEquals(5, lastId);
    }
}
