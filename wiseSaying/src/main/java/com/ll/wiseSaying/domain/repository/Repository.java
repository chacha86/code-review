package com.ll.wiseSaying.domain.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ll.wiseSaying.domain.WiseSaying;

public class Repository {

	private static final Repository instance = new Repository();

	public static synchronized Repository getInstance() {
		return instance;
	}

	List<WiseSaying> wiseSayings = new ArrayList<>();

	public WiseSaying save(WiseSaying wiseSaying) {
		wiseSayings.addFirst(wiseSaying);
		return wiseSaying;
	}

	public void saveJson(WiseSaying wiseSaying) {
		try (FileWriter file = new FileWriter("wiseSaying/db/wiseSaying/" + wiseSaying.getId() + ".json")) {
			file.write(wiseSaying.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDataJson(List<WiseSaying> wiseSayingList) {
		String json = WiseSaying.toJsonArray(wiseSayingList);
		try (FileWriter file = new FileWriter("wiseSaying/db/wiseSaying/data.json")) {
			file.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveTxt(int lastId) {
		try (FileWriter file = new FileWriter("wiseSaying/db/wiseSaying/lastId.txt")) {
			file.write(Integer.toString(lastId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readJson() throws FileNotFoundException {
		int lastId = Integer.parseInt(new Scanner(new File("wiseSaying/db/wiseSaying/lastId.txt")).next());
		for (int i = lastId; i > 0; i--) {
			try (BufferedReader reader = new BufferedReader(new FileReader("wiseSaying/db/wiseSaying/" + i + ".json"))) {
				StringBuilder jsonContent = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					jsonContent.append(line);
				}

				String[] keyValues = jsonContent.toString()
					.replace("}", "")
					.split(",");
				int id = Integer.parseInt(keyValues[0].split(":")[1].trim());
				String author = keyValues[1].split(":")[1].replace("\"", "").trim();
				String content = keyValues[2].split(":")[1].replace("\"", "").trim();

				wiseSayings.add(new WiseSaying(id, author, content));
			} catch (FileNotFoundException e) {}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<WiseSaying> findAll() {
		return wiseSayings;
	}

	public WiseSaying findById(int id) {
		for (WiseSaying wiseSaying : wiseSayings) {
			if (wiseSaying.getId() == id) {
				return wiseSaying;
			}
		}
		return null;
	}

	public void delete(WiseSaying wiseSaying) {
		wiseSayings.remove(wiseSaying);
		new File("wiseSaying/db/wiseSaying/" + wiseSaying.getId() + ".json").delete();
	}

	public List<WiseSaying> searchByAuthor(String content) {
		List<WiseSaying> searchWiseSayings = new ArrayList<>();

		for (WiseSaying wiseSaying : wiseSayings) {
			if(wiseSaying.getAuthor().contains(content)) {
				searchWiseSayings.add(wiseSaying);
			}
		}

		return searchWiseSayings;
	}

	public List<WiseSaying> searchByContent(String content) {
		List<WiseSaying> searchWiseSayings = new ArrayList<>();

		for (WiseSaying wiseSaying : wiseSayings) {
			if (wiseSaying.getContent().contains(content)) {
				searchWiseSayings.add(wiseSaying);
			}
		}

		return searchWiseSayings;
	}
}
