package TDD.main.service;

import TDD.Proverb;
import TDD.main.repository.ProverbRepository;

import java.util.ArrayList;
import java.util.List;

public class ProverbService {
    private final ProverbRepository repository;
    private final List<Proverb> proverbList;

    public ProverbService() {
        this.repository = new ProverbRepository();
        this.proverbList = repository.loadProverbs();
    }

    public String addProverb(String proverb, String author) {
        int id = repository.getNextId();
        Proverb newProverb = new Proverb(id, proverb, author);
        proverbList.add(newProverb);
        repository.saveProverb(newProverb);
        return id + "번 명언이 등록되었습니다.";
    }

    public String getProverbsByPage(int page) {
        int itemsPerPage = 5;
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, proverbList.size());

        if (startIndex >= proverbList.size() || startIndex < 0) {
            return "존재하지 않는 페이지입니다.";
        }

        StringBuilder result = new StringBuilder("번호 / 작가 / 명언\n----------------\n");
        for (int i = startIndex; i < endIndex; i++) {
            Proverb proverb = proverbList.get(i);
            result.append(proverb.getId()).append(" / ").append(proverb.getAuthor())
                    .append(" / ").append(proverb.getProverb()).append("\n");
        }
        return result.toString();
    }

    public String searchProverb(String keywordType, String keyword) {
        List<Proverb> results = new ArrayList<>();
        for (Proverb proverb : proverbList) {
            if ("content".equals(keywordType) && proverb.getProverb().contains(keyword)) {
                results.add(proverb);
            } else if ("author".equals(keywordType) && proverb.getAuthor().contains(keyword)) {
                results.add(proverb);
            }
        }

        if (results.isEmpty()) {
            return "검색 결과가 없습니다.";
        }

        StringBuilder result = new StringBuilder("번호 / 작가 / 명언\n----------------\n");
        for (Proverb proverb : results) {
            result.append(proverb.getId()).append(" / ").append(proverb.getAuthor())
                    .append(" / ").append(proverb.getProverb()).append("\n");
        }
        return result.toString();
    }

    public String deleteProverb(int id) {
        for (int i = 0; i < proverbList.size(); i++) {
            if (proverbList.get(i).getId() == id) {
                proverbList.remove(i);
                repository.deleteProverbFile(id);
                return id + "번 명언이 삭제되었습니다.";
            }
        }
        return id + "번 명언은 존재하지 않습니다.";
    }

    public String updateProverb(int id, String newProverb, String newAuthor) {
        for (Proverb proverb : proverbList) {
            if (proverb.getId() == id) {
                proverb.setProverb(newProverb);
                proverb.setAuthor(newAuthor);
                repository.saveProverb(proverb);
                return id + "번 명언이 수정되었습니다.";
            }
        }
        return id + "번 명언은 존재하지 않습니다.";
    }

    public void saveProverbsToData() {
        repository.saveProverbsToData(proverbList);
    }
}
