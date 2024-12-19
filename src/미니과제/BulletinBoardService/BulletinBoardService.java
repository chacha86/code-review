package src.미니과제.BulletinBoardService;

import src.미니과제.BulletinBoardRepository.BulletinBoardRepository;
import 미니과제.Proverb;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BulletinBoardService {
    private final BulletinBoardRepository repository;
    private final List<Proverb> proverbList;

    public BulletinBoardService() {
        this.repository = new BulletinBoardRepository();
        this.proverbList = repository.loadProverbs();
    }

    public void addProverb(String proverb, String author) {
        int id = repository.readLastId();
        Proverb proverbObject = new Proverb(id, proverb, author);
        proverbList.add(proverbObject);
        repository.saveProverb(proverbObject);
        repository.saveLastId(id + 1);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void listProverbs(int page) {
        int itemsPerPage = 5;
        int totalItems = proverbList.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (page < 1 || page > totalPages) {
            System.out.println("존재하지 않는 페이지입니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");

        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        for (int i = startIndex; i < endIndex; i++) {
            System.out.println(proverbList.get(i).toString());
        }

        System.out.print("페이지: [");
        for (int i = 1; i <= totalPages; i++) {
            System.out.print(i == page ? " " + i + " " : " " + i + " ");
        }
        System.out.println("]");
    }

    public void searchProverb(String keywordType, String keyword) {
        List<Proverb> results = new ArrayList<>();

        for (Proverb proverb : proverbList) {
            if (keywordType.equals("content") && proverb.getProverb().contains(keyword)) {
                results.add(proverb);
            } else if (keywordType.equals("author") && proverb.getAuthor().contains(keyword)) {
                results.add(proverb);
            }
        }

        if (results.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");
        for (Proverb proverb : results) {
            System.out.println(proverb.toString());
        }
    }

    public void deleteProverb(int id) {
        boolean check = false;

        for (int i = 0; i < proverbList.size(); i++) {
            if (proverbList.get(i).getId() == id) {
                proverbList.remove(i);
                repository.deleteProverbFile(id);
                System.out.println(id + "번째 명언이 삭제되었습니다.");
                check = true;
                break;
            }
        }

        if (!check || id <= 0) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void updateProverb(int id, Scanner scanner) { // Scanner 객체를 매개변수로 받기
        for (Proverb proverb : proverbList) {
            if (proverb.getId() == id) {
                System.out.println("명언(기존) : " + proverb.getProverb());
                System.out.print("명언 : ");
                String newProverb = scanner.nextLine();
                proverb.setProverb(newProverb);

                System.out.println("작가(기존) : " + proverb.getAuthor());
                System.out.print("작가 : ");
                String newAuthor = scanner.nextLine();
                proverb.setAuthor(newAuthor);

                repository.saveProverb(proverb);
                return;
            }
        }
        System.out.println(id + "번 명언은 존재하지 않습니다.");
    }

    public void saveProverbsToData() {
        repository.saveProverbsToData(proverbList);
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
