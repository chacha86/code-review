package com.ll.wiseSaying.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ll.wiseSaying.domain.WiseSaying;
import com.ll.wiseSaying.service.CommandService;
import com.ll.wiseSaying.service.QueryService;

public class Controller {

	private static final Controller instance = new Controller();

	public static synchronized Controller getInstance() {
		return instance;
	}

	private final CommandService commandService = CommandService.getInstance();
	private final QueryService queryService = QueryService.getInstance();

	public void save(Scanner sc) {
		System.out.print("명언 : ");
		String content = sc.nextLine();
		System.out.print("작가 : ");
		String author = sc.nextLine();
		System.out.println(commandService.save(author, content).getId() + "번 명언이 등록되었습니다.");
	}

	public void readAll(String command) {
		List<WiseSaying> wiseSayings;

		if(command.contains("keyword")) {
			System.out.println("----------------------\n"
				+ "검색타입 : " + command.split("&")[0].split("=")[1]
				+ "\n검색어 : " + command.split("&")[1].split("=")[1]
				+ "\n----------------------");
			wiseSayings = queryService.search(
				command.split("&")[0].split("=")[1],
				command.split("&")[1].split("=")[1]
			);
		} else {
			wiseSayings = queryService.readAll();
		}

		int pageSize = 5;
		int totalItems = wiseSayings.size();
		int totalPages = (int)Math.ceil((double)totalItems / pageSize);
		int currentPage = 1;

		if (command.contains("page")) {
			currentPage = Integer.parseInt(command.split("\\?")[1].split("=")[1]);
		}

		int startIndex = (currentPage - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, totalItems);
		wiseSayings = wiseSayings.subList(startIndex, endIndex);

		System.out.println("번호 / 작가 / 명언");
		System.out.println("----------------------");

		for (WiseSaying wiseSaying : wiseSayings) {
			System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
		}

		System.out.print("페이지 : ");
		System.out.println("[" + currentPage + "]" + " / " + totalPages);
	}

	public void delete(String command) {
		int id = Integer.parseInt(command.split("=")[1]);
		WiseSaying wiseSaying = queryService.readById(id);
		if (wiseSaying == null) {
			System.out.println(id + "번 명언은 존재하지 않습니다.");
			return;
		}
		commandService.delete(wiseSaying);
		System.out.println(id + "번 명언이 삭제되었습니다.");
	}

	public void update(Scanner sc, String command) {
		int id = Integer.parseInt(command.split("=")[1]);
		WiseSaying updatable = queryService.readById(id);
		if (updatable == null) {
			System.out.println(id + "번 명언은 존재하지 않습니다.");
			return;
		}
		System.out.println("명언(기존) : " + updatable.getContent());
		System.out.print("명언 : ");
		String content = sc.nextLine();
		System.out.println("작가(기존) : " + updatable.getAuthor());
		System.out.print("작가 : ");
		String author = sc.nextLine();

		commandService.update(updatable, content, author);
	}

	public void exit() {
		commandService.exit();
	}

	public void saveDataJson() {
		commandService.saveDataJson();
		System.out.println("data.json 파일의 내용이 갱신되었습니다.");
	}

	public void readJson() {
		try {
			queryService.readJson();
		} catch (FileNotFoundException e) {
		}
	}
}
