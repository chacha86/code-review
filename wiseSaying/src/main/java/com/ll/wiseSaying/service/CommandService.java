package com.ll.wiseSaying.service;

import java.util.List;

import com.ll.wiseSaying.domain.WiseSaying;
import com.ll.wiseSaying.domain.repository.Repository;

public class CommandService {

	private static final CommandService instance = new CommandService();

	public static synchronized CommandService getInstance() {
		return instance;
	}

	private final Repository repository = Repository.getInstance();

	public WiseSaying save(String author, String content) {
		WiseSaying wiseSaying;
		if (repository.findAll().isEmpty()) {
			wiseSaying = repository.save(new WiseSaying(1, author, content));
		}
		else {
			wiseSaying = repository.save(new WiseSaying(repository.findAll().getFirst().getId() + 1, author, content));
		}
		repository.saveJson(wiseSaying);
		return wiseSaying;
	}

	public void saveDataJson() {
		repository.saveDataJson(repository.findAll());
	}

	public void delete(WiseSaying wiseSaying) {
		repository.delete(wiseSaying);
	}

	public void update(WiseSaying updatable, String content, String author) {
		updatable.update(author, content);
		repository.saveJson(updatable);
	}

	public void exit() {
		List<WiseSaying> wiseSayings = repository.findAll();
		repository.saveTxt(wiseSayings.getFirst().getId());
	}
}
