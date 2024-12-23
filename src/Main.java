import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String action = "";
        String content = "";
        String author = "";

        int targetId = 0;
        int id = 1;
        if (getLastId() != 1) {
            id = getLastId() + 1;
        }

        ArrayList<WiseSaying> list = load();

        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");

        while (!action.equals("종료")) {
            System.out.print("명령) ");
            action = sc.nextLine();

            switch (action) {
                case "등록":
                    System.out.print("명언 : ");
                    content = sc.nextLine();
                    System.out.print("작가 : ");
                    author = sc.nextLine();

                    WiseSaying saying = new WiseSaying(id, content, author);
                    save(saying);
                    list.add(saying);
                    System.out.println(id + "번 명언이 등록되었습니다.");

                    id++;
                    updateLastId(id);
                    break;

                case "목록":
                    Collections.sort(list, new Comparator<WiseSaying>() {
                        public int compare(WiseSaying ws1, WiseSaying ws2) {
                            return Integer.compare(ws2.getId(), ws1.getId());
                        }
                    });

                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");

                    if (list.isEmpty()) {
                        System.out.println("등록된 명언이 없습니다.");
                    } else {
                        for (WiseSaying ws : list) {
                            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
                        }
                    }
                    break;

                case "삭제":
                    System.out.print("id=");
                    targetId = sc.nextInt();
                    sc.nextLine();

                    WiseSaying deleteWS = getSaying(list, targetId);
                    if (deleteWS != null) {
                        list.remove(deleteWS);
                        delete(deleteWS);
                    }
                    break;

                case "수정":
                    System.out.print("id=");
                    targetId = sc.nextInt();
                    sc.nextLine();

                    WiseSaying updateWS = getSaying(list, targetId);
                    if (updateWS != null) {
                        System.out.println("명언(기존) : " + updateWS.getContent());
                        System.out.print("명언 : ");
                        content = sc.nextLine();

                        System.out.println("작가(기존) : " + updateWS.getAuthor());
                        System.out.print("작가 : ");
                        author = sc.nextLine();

                        updateWS.setContent(content);
                        updateWS.setAuthor(author);
                        save(updateWS);
                        System.out.println(targetId + "번 명언이 수정되었습니다.");
                    } else {
                        System.out.println(targetId + "번 명언은 존재하지 않습니다.");
                    }
                    break;

                case "빌드":
                    build(list);
                    break;

                case "종료":
                    if (!list.isEmpty()) {
                        updateLastId(id - 1);
                    } else {
                        updateLastId(1);
                    }
                    break;

                default:
                    System.out.println("올바른 명령어를 입력해주세요.");
                    break;
            }
        }

        sc.close();
    }

    public static int getLastId() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("db/wiseSaying/lastId.txt"));
            String lastId = reader.readLine();
            reader.close();
            return Integer.parseInt(lastId);
        } catch (IOException e) {
            return 1;
        }
    }

    public static void updateLastId(int id) {
        try {
            FileWriter writer = new FileWriter("db/wiseSaying/lastId.txt");
            writer.write(String.valueOf(id));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(WiseSaying ws) {
        try {
            String json = ws.toJson();

            File file = new File("db/wiseSaying/" + ws.getId() + ".json");
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(WiseSaying ws) {
        try {
            File file = new File("db/wiseSaying/" + ws.getId() + ".json");
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(ws.getId() + "번 명언이 삭제되었습니다.");
                }
            } else {
                System.out.println(ws.getId() + "번 명언은 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void build(ArrayList<WiseSaying> list) {
        try {
            StringBuilder jsonBuilder = new StringBuilder("[\n");

            for (int i = 0; i < list.size(); i++) {
                jsonBuilder.append("\t{\n")
                        .append("\t\t\"id\": ").append(list.get(i).getId()).append(",\n")
                        .append("\t\t\"content\": \"").append(list.get(i).getContent()).append("\",\n")
                        .append("\t\t\"author\": \"").append(list.get(i).getAuthor()).append("\"\n")
                        .append("\t}");
                if (i < list.size() - 1) {
                    jsonBuilder.append(",");
                }
                jsonBuilder.append("\n");
            }
            jsonBuilder.append("]");

            File file = new File("db/wiseSaying/data.json");
            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);
            writer.write(jsonBuilder.toString());
            writer.close();

            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<WiseSaying> load() {
        ArrayList<WiseSaying> list = new ArrayList<WiseSaying>();
        File dir = new File("db/wiseSaying");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                if (file.getName().equals("data.json")) {
                    continue;
                }

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json.append(line);
                    }
                    reader.close();

                    WiseSaying ws = WiseSaying.fromJson(json.toString());
                    list.add(ws);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static WiseSaying getSaying(ArrayList<WiseSaying> list, int id) {
        for (WiseSaying ws : list) {
            if (ws.getId() == id) {
                return ws;
            }
        }
        return null;
    }
}
