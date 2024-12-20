package com.ll.wiseSaying;

import java.util.Scanner;

import com.ll.wiseSaying.controller.Controller;

public class App {

	private static final App instance = new App();

	public static synchronized App getInstance() {
		return instance;
	}

	private final Controller controller = Controller.getInstance();

	public void run() {
		Scanner sc = new Scanner(System.in);

		controller.readJson();

		System.out.println("== 명언 앱 ==");
		while (true) {
			System.out.print("명령) ");
			String command = sc.nextLine();
			switch (command.split("\\?")[0]) {
				case "종료" -> {
					controller.exit();
					return;
				}
				case "빌드" -> controller.saveDataJson();
				case "등록" -> controller.save(sc);
				case "목록" -> controller.readAll(command);
				case "삭제" -> controller.delete(command);
				case "수정" -> controller.update(sc, command);
			}
		}
	}
}