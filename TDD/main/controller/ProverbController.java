package TDD.main.controller;


import TDD.main.service.ProverbService;

public class ProverbController {

    private final ProverbService service;

    public ProverbController() {
        this.service = new ProverbService();
    }

    public String registerProverb(String proverb, String author) {
        if (proverb == null || proverb.isBlank() || author == null || author.isBlank()) {
            return "명언과 작가는 반드시 입력해야 합니다.";
        }
        return service.addProverb(proverb, author);
    }

    public String listProverbs(int page) {
        return service.getProverbsByPage(page);
    }

    public String searchProverb(String keywordType, String keyword) {
        return service.searchProverb(keywordType, keyword);
    }

    public String deleteProverb(int id) {
        return service.deleteProverb(id);
    }

    public String updateProverb(int id, String newProverb, String newAuthor) {
        return service.updateProverb(id, newProverb, newAuthor);
    }

    public String saveDataToFile() {
        service.saveProverbsToData();
        return "data.json 파일이 생성되었습니다.";
    }

}
