package wiseSaying;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
public class WiseSayingController {

    private final static WiseSayingController instance = new WiseSayingController();
    private WiseSayingController(){}
    public static synchronized WiseSayingController getInstance() {
        return instance;
    }

    public final static int pagingSize = 5;


    private WiseSayingService service = WiseSayingService.getInstance();

    public Long register() {
        System.out.print("명언 : ");
        String content = inputReader();
        System.out.print("작가 : ");
        String author = inputReader();

        Long savedId = service.register(content, author);
        System.out.println(savedId + "번 명언이 등록되었습니다.");
        return savedId;
    }


    public void showList() {
//        Set<WiseSaying> wiseSet = service.findAllWise();

        Set<WiseSaying> wiseSet = service.search(null, null);

        List<WiseSaying> wiseSayings = new ArrayList<>();
        for (WiseSaying wiseSaying : wiseSet) {
            wiseSayings.add(wiseSaying);
        }
        wiseSayings.sort((a,b) -> (int) (b.getId() - a.getId()));

        System.out.println("번호  /  작가  /  명언");
        System.out.println("----------------------");

        int size = pagingSize;
        if (wiseSayings.size() < pagingSize) {
            size = wiseSayings.size();
        }

        for (int i = 0; i < size; i++) {
            System.out.println(wiseSayings.get(i).getId() + " / " + wiseSayings.get(i).getAuthor() + " / " + wiseSayings.get(i).getContent());
        }
    }

    public void search(String condition) {
        Map<String, String> conditionMap = parseParameters(condition);
        String keywordType;
        String keyword;
        if (conditionMap.containsKey("keywordType")) {
            keywordType = conditionMap.get("keywordType");
        } else {
            keywordType = null;
        }

        if (conditionMap.containsKey("keyword")) {
            keyword = conditionMap.get("keyword");
        } else {
            keyword = null;
        }

        Set<WiseSaying> searchSet = service.search(keywordType, keyword);

        if (conditionMap.containsKey("page")) {
            int page = Integer.parseInt(conditionMap.get("page"));
            List<WiseSaying> wiseSayings = new ArrayList<>();
            for (WiseSaying wiseSaying : searchSet) {
                wiseSayings.add(wiseSaying);
            }
            wiseSayings.sort((a,b) -> (int) (b.getId() - a.getId()));
            int itemsPerPage = pagingSize; // 페이지당 명언 수
            int totalItems = wiseSayings.size(); // 전체 명언 수
            int totalPages = (totalItems + itemsPerPage - 1) / itemsPerPage; // 전체 페이지 수 계산

            int startIndex = (page - 1) * itemsPerPage; // 현재 페이지의 시작 인덱스
            if (startIndex >= totalItems || startIndex < 0) {
                System.out.println("잘못된 페이지 번호입니다.");
                return; // 유효하지 않은 페이지 번호일 경우 처리 중단
            }

            int endIndex = Math.min(startIndex + itemsPerPage, totalItems); // 현재 페이지의 끝 인덱스
            List<WiseSaying> pageItems = wiseSayings.subList(startIndex, endIndex); // 현재 페이지에 해당하는 명언 목록 추출

            System.out.println("번호  /  작가  /  명언");
            System.out.println("----------------------");
            for (WiseSaying wise : pageItems) {
                System.out.println(wise.getId() + " / " + wise.getAuthor() + " / " + wise.getContent());
            }
            // 페이지 네비게이션 정보 출력
            System.out.print("페이지 : ");
            for (int i = 1; i <= totalPages; i++) {
                if (i == page) {
                    System.out.print("[" + i + "] ");
                } else {
                    System.out.print(i + " ");
                }
            }
            System.out.println();



        } else {
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");
            System.out.println("번호  /  작가  /  명언");
            System.out.println("----------------------");
            for (WiseSaying wise : searchSet) {
                System.out.println(wise.getId() + " / " + wise.getAuthor() + " / " + wise.getContent());
            }
        }




    }

    public void remove(String command) {
        if (command.contains("?")) {
            Map<String, String> conditionMap = parseParameters(command);
            Long removeId = Long.parseLong(conditionMap.get("id"));

            Long removedId = service.remove(removeId);
            if (removedId != null) {
                System.out.println(removedId + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(removeId + "번 명언은 존재하지 않습니다.");
            }
        } else {
            System.out.println("id 값 입력이 필요합니다.");
        }

    }

    public void modify(String command) {
        if (!command.contains("?")) {
            System.out.println("id 값 입력이 필요합니다.");
            return;
        }

        Map<String, String> map = parseParameters(command);
        Long modifyId = Long.parseLong(map.get("id"));
        WiseSaying modifyWise = service.searchOneWise(modifyId);
        if (modifyWise == null) {
            System.out.println(modifyId + "번 명언은 존재하지 않습니다");
            return;
        }

        System.out.println("명언(기존) : " + modifyWise.getContent());
        System.out.print("명언 : ");
        String newContent = inputReader();

        System.out.println("작가(기존) : " + modifyWise.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = inputReader();

        WiseSaying modifiedWise = service.modify(modifyId, newContent, newAuthor);
        if (modifiedWise == null) {
            System.out.println(modifyId + "번 명언 수정에 실패 하였습니다.");
            return;
        }
    }


    public void build() {
        System.out.println("WiseSayingController.build");
        Boolean buildResult = service.build();
        if (buildResult) {
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } else {
            System.out.println("data.json 파일 저장에 실패했습니다.");
        }

    }

    public void exit() {
        System.exit(0);
    }


    private String inputReader() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = bf.readLine();
            return input;
        } catch (IOException e) {
            log.error("input error", e);
            return null;
        }
    }

    private static Map<String, String> parseParameters(String input) {
        Map<String, String> params = new HashMap<>();
        String[] parts = input.split("\\?");
        if (parts.length > 1) {
            String[] queries = parts[1].split("&");
            for (String query : queries) {
                String[] keyValue = query.split("=");
                if (keyValue.length > 1) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}
