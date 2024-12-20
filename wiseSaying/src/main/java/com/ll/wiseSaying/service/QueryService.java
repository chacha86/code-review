package com.ll.wiseSaying.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.ll.wiseSaying.domain.WiseSaying;
import com.ll.wiseSaying.domain.repository.Repository;

public class QueryService {

	private static final QueryService instance = new QueryService();

	public static synchronized QueryService getInstance() {
		return instance;
	}

	private final Repository repository = Repository.getInstance();

	public List<WiseSaying> readAll() {
		return repository.findAll();
	}

	public WiseSaying readById(int id) {
		return repository.findById(id);
	}

	public void readJson() throws FileNotFoundException {
		repository.readJson();
	}

	public List<WiseSaying> search(String type, String content) {
		if (type.equals("author")) {
			return repository.searchByAuthor(content);
		}
		return repository.searchByContent(content);
	}
}
