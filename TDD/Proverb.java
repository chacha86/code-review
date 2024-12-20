package TDD;

public class Proverb {

    private int id;           // 명언 ID
    private String proverb;   // 명언 내용
    private String author;    // 작가 이름

    // 생성자
    public Proverb(int id, String proverb, String author) {
        this.id = id;
        this.proverb = proverb;
        this.author = author;
    }

    // Getter와 Setter
    public int getId() {
        return id;
    }

    public String getProverb() {
        return proverb;
    }

    public void setProverb(String proverb) {
        this.proverb = proverb;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // toString 메서드 (목록 출력용)
    @Override
    public String toString() {
        return id + " / " + author + " / " + proverb;
    }
}
