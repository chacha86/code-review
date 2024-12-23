package com.example.wisesaying.service.impl;

import com.example.wisesaying.domain.WiseSaying;
import com.example.wisesaying.service.WiseSayingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class WiseSayingServiceImpl implements WiseSayingService {

    private final String DB_FOLDER = "db/wiseSaying";

    private final ArrayList<WiseSaying> wiseSayingArrayList = new ArrayList<>();
    private long n = 1;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public WiseSayingServiceImpl() {
        // db/wiseSaying 폴더 생성
        File folder = new File(DB_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // lastId.txt 파일 생성 (없을 경우)
        File lastIdFile = new File(DB_FOLDER + "/lastId.txt");
        if (!lastIdFile.exists()) {
            try (FileWriter writer = new FileWriter(lastIdFile)) {
                writer.write("1"); // 초기값 1
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // lastId.txt 수정
    private void updateLastId(long id) throws IOException {
        try (FileWriter writer = new FileWriter(DB_FOLDER + "/lastId.txt")) {
            writer.write(String.valueOf(id));
        }
    }

    /*// lastId.txt 읽기
    private long getLastId() throws IOException {
        String lastIdContent = new String(Files.readAllBytes(Paths.get(DB_FOLDER + "/lastId.txt")));
        return Long.parseLong(lastIdContent.trim());
    }*/

    @Override
    public void register() throws IOException {

        String content = "";
        String author = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            content = br.readLine();
            author = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("명언 : " + content);
        System.out.println("작가 : " + author);
        System.out.println(n + "번 명언이 등록되었습니다.");

        wiseSayingArrayList.add(new WiseSaying(n, content, author));

        // 파일생성
        saveToFile(new WiseSaying(n, content, author));

        File lastIdFile = new File(DB_FOLDER + "/lastId.txt");
        if (lastIdFile.exists()) {
            System.out.println("txt 파일 존재");
            try {
                updateLastId(n);
                System.out.println(n);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        n += 1;
    }

    // JSON 파일로 저장
    private void saveToFile(WiseSaying wiseSaying) throws IOException {
        File file = new File(DB_FOLDER + "/" + wiseSaying.getId() + ".json");
        objectMapper.writeValue(file, wiseSaying);
    }

    @Override
    public void getList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------------");

        for (int i = wiseSayingArrayList.size() - 1; i >= 0; i--) {
            WiseSaying w = wiseSayingArrayList.get(i);
            System.out.println(w.getId() + " / " + w.getAuthor() + " / " + w.getContent());
        }
    }

    @Override
    public void delete() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int number = Integer.parseInt(br.readLine());
            for (int i = 0; i < wiseSayingArrayList.size(); i++) {
                if (wiseSayingArrayList.get(i).getId() == number) {
                    // 삭제하기
                    wiseSayingArrayList.remove(i);
                    System.out.println(number + "번 명언이 삭제되었습니다.");
                    break;
                } else {
                    System.out.println(number + "번 명언은 존재하지 않습니다.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int number = Integer.parseInt(br.readLine());
            for (int i = 0; i < wiseSayingArrayList.size(); i++) {
                if (wiseSayingArrayList.get(i).getId() == number) {
                    // 수정하기
                    System.out.println("명언(기존) : " + wiseSayingArrayList.get(i).getContent());
                    String content = br.readLine();

                    wiseSayingArrayList.get(i).setContent(content);

                    System.out.println("명언 : " + content);

                    System.out.println("작가(기존) : " + wiseSayingArrayList.get(i).getAuthor());

                    String author = br.readLine();

                    wiseSayingArrayList.get(i).setAuthor(author);
                    System.out.println("작가 : " + author);

                    break;
                } else {
                    System.out.println(number + "번 명언은 존재하지 않습니다.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBuild() {
        try {
            File file = new File(DB_FOLDER + "/" + "data" + ".json");
            objectMapper.writeValue(file, wiseSayingArrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }


}
