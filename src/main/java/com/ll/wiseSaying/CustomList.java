package com.ll.wiseSaying;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// List non Library 용
public class CustomList {
    private WiseSaying[] wiseSayings;
    private int listSize;
    private static final int PAGE_SIZE = 5; // 페이징 기능 사이즈 5

    public CustomList() {
        // 초기값 10(생성 10개로 인해)
        wiseSayings = new WiseSaying[10];
        listSize = 0;
    }

    // 10개에서 listSize에 맞춰서 크거나 작게 변경 더해지면 -> 배열크기 늘리고, 배열값을 다시 넣어줘야됨
    public void add(WiseSaying wiseSaying) {
        if (listSize == wiseSayings.length) {
            resize();
        }
        wiseSayings[listSize] = wiseSaying;
        listSize++;
    }

    public WiseSaying getWiseSaying(int idx) {
        for(int i = 0; i < listSize; i++) {
            if(wiseSayings[i].idx == idx) {
                return wiseSayings[i];
            }
        }

        return null;
    }

    public void modifyWiseSaying(int idx, String modifySaying, String modifyAuthor) {
        for(int i = 0; i < listSize; i++) {
            if(wiseSayings[i].idx == idx) {
                wiseSayings[i].setSaying(modifySaying);
                wiseSayings[i].setAuthor(modifyAuthor);
            }
        }
    }

    // 배열지울때 -> 값 찾아서 지우면, 해당 배열을 한칸씩 앞으로 놓고 마지막 배열을 null로 두고, listSize의 크기를 하나 줄이면됩니다.
    public void remove(int idx) {
        int rmIdx = -1;

        for(int i = 0; i < listSize; i++) {
            if(wiseSayings[i].idx == idx) {
                rmIdx = i;
                break;
            }
        }

        if(rmIdx == -1) {
            System.out.println(idx + "번 명언은 존재하지 않습니다.");
            return;
        }

        for (int i = rmIdx; i < listSize - 1; i++) {
            wiseSayings[i] = wiseSayings[i + 1];
        }

        wiseSayings[listSize - 1] = null;

        listSize--;

        System.out.println(idx + "번 명언이 삭제되었습니다.");
    }

    public int size() {
        return listSize;
    }

    void resize() {
        WiseSaying[] newList = new WiseSaying[wiseSayings.length * 2];
        for (int i = 0; i < wiseSayings.length; i++) {
            newList[i] = wiseSayings[i];
        }
        wiseSayings = newList;
    }

    public void saveSingleWiseSaying(WiseSaying wiseSaying) {
        createDirectoryIfNeeded();
        String json = "{\n" +
                "  \"id\": " + wiseSaying.getIdx() + ",\n" +
                "  \"content\": \"" + wiseSaying.getSaying() + "\",\n" +
                "  \"author\": \"" + wiseSaying.getAuthor() + "\"\n" +
                "}";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/wiseSaying/" + wiseSaying.getIdx() + ".json"))) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteSingleWiseSaying(int idx) {
        createDirectoryIfNeeded();
        File file = new File("db/wiseSaying/" + idx + ".json");
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println(idx + ".json 파일 삭제 실패!");
            }
        } else {
            System.out.println(idx + ".json 파일이 존재하지 않습니다.");
        }
    }

    public void saveFile() {

        createDirectoryIfNeeded();

        for (int i = 0; i < listSize; i++) {
            String json = "{\n" +
                    "  \"id\": " + wiseSayings[i].getIdx() + ",\n" +
                    "  \"content\": \"" + wiseSayings[i].getSaying() + "\",\n" +
                    "  \"author\": \"" + wiseSayings[i].getAuthor() + "\"\n" +
                    "}";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/wiseSaying/" + wiseSayings[i].idx + ".json"))) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db/wiseSaying/lastId.txt"))) {
            writer.write(Integer.toString(listSize));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void totalJsonFileSave() {

        createDirectoryIfNeeded();

        String json = "[\n";

        for (int i = 0; i < listSize; i++) {
            json += "  {\n" +
                    "    \"id\": " + wiseSayings[i].getIdx() + ",\n" +
                    "    \"content\": \"" + wiseSayings[i].getSaying() + "\",\n" +
                    "    \"author\": \"" + wiseSayings[i].getAuthor() + "\"\n" +
                    "  }";

            if(listSize - 1 != i) {
                json += ",";
            }
        }

        json += "\n" +
                "]";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.json"))) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createDirectoryIfNeeded() {
        File directory = new File("db/wiseSaying");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public WiseSaying get(int index) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return wiseSayings[index];
    }

    public CustomList searchByKeyword(String keywordType, String keyword) {
        CustomList searchResult = new CustomList();

        for(int i = 0; i < listSize; i++) {
            if(keywordType.equals("content")) {
                if(wiseSayings[i].getSaying().contains(keyword)) {
                    searchResult.add(wiseSayings[i]);
                }
            } else if(keywordType.equals("author")) {
                if(wiseSayings[i].getAuthor().contains(keyword)) {
                    searchResult.add(wiseSayings[i]);
                }
            }
        }

        return searchResult;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) listSize / PAGE_SIZE);
    }

    public CustomList getPage(int page) {
        CustomList pageList = new CustomList();
        int startIdx = (page - 1) * PAGE_SIZE;
        int endIdx = Math.min(startIdx + PAGE_SIZE, listSize); // 5보다 작은 경우도

        // 최신글이 먼저 나오도록 역순으로
        for(int i = listSize - 1 - startIdx; i >= listSize - endIdx; i--) {
            if(i >= 0) {
                pageList.add(wiseSayings[i]);
            }
        }
        return pageList;
    }

    public void printPageInfo(int currentPage) {
        System.out.println("----------------------");
        int totalPages = getTotalPages();

        System.out.print("페이지 : ");
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                System.out.print("[" + i + "]");
            } else {
                System.out.print(i);
            }
            if (i < totalPages) {
                System.out.print(" / ");
            }
        }
        System.out.println();
    }
}
