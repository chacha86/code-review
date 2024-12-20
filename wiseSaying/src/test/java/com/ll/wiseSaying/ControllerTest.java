package com.ll.wiseSaying;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ll.wiseSaying.controller.Controller;

public class ControllerTest {

	private ByteArrayOutputStream outContent;
	private Controller controller;

	@BeforeEach
	void beforeEach() {
		controller = Controller.getInstance();
		outContent = TestUtil.setOutToByteArray();
	}

	@AfterEach
	void afterEach() {
		TestUtil.clearSetOutToByteArray(outContent);
	}

	@Test
	void 명언을_등록하다() {
		// given
		Scanner sc = createInput();

		// when
		controller.save(sc);

		// then
		assertThat(outContent.toString())
			.contains("명언 : ")
			.contains("작가 : ")
			.contains("1번 명언이 등록되었습니다.");
	}

	@Test
	void 명언을_삭제하다() {
		// given
		Scanner input = createInput();
		controller.save(input);
		String command = "삭제?id=1";

		// when
		controller.delete(command);

		// then
		assertThat(outContent.toString())
			.contains("1번 명언이 삭제되었습니다.");
	}

	@Test
	void 명언_목록을_조회한다() {
		// given
		Scanner input = createInput();
		controller.save(input);

		// when
		controller.readAll();

		// then
		assertThat(outContent.toString())
			.contains("1 / 테스트 / 테스트");
	}

	@Test
	void 명언을_삭제한다() {
		// given
		Scanner input = createInput();
		controller.save(input);
		String command = "수정?id=1";
		Scanner update = TestUtil.genScanner("수정\n수정\n");

		// when
		controller.update(update, command);

		// then
		assertThat(outContent.toString())
			.contains("명언(기존) : 테스트")
			.contains("작가(기존) : 테스트");
	}

	@Test
	void 파일을_빌드한다() {
		// when
		controller.saveDataJson();

		// then
		assertThat(outContent.toString())
			.contains("data.json 파일의 내용이 갱신되었습니다.");
	}

	private static Scanner createInput() {
		String input = "테스트\n테스트\n";
		Scanner sc = TestUtil.genScanner(input);
		return sc;
	}
}
