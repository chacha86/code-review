package com.ll.wiseSaying.domain;

import java.util.List;

public class WiseSaying {

	private int id = 0;
	private String author;
	private String content;

	public WiseSaying(int id, String author, String content) {
		this.id = id;
		this.author = author;
		this.content = content;
	}

	public void update(String author, String content) {
		this.author = author;
		this.content = content;
	}

	public String toJson() {
		return "{\n\t\"id\": " + id +
			",\n\t\"author\": \"" + author +
			"\",\n\t\"content\": \"" + content +
			"\"\n}";
	}

	public static String toJsonArray(List<WiseSaying> wiseSayingList) {
		StringBuilder jsonArray = new StringBuilder("[\n");
		for (int i = 0; i < wiseSayingList.size(); i++) {
			jsonArray.append(wiseSayingList.get(i).toJson());
			if (i < wiseSayingList.size() - 1) {
				jsonArray.append(",\n");
			}
		}

		jsonArray.append("\n]");
		return jsonArray.toString();
	}

	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getContent() {
		return content;
	}
}