package 미니과제;

public class Proverb {
    private int id;  // id 필드를 private로 유지
    private String proverb;  // private으로 변경
    private String author;    // private으로 변경

    public Proverb(int id, String proverb, String author) {
        this.id = id;
        this.proverb = proverb;
        this.author = author;
    }

    // 접근자 메서드 추가
    public int getId() {
        return id;
    }

    public String getProverb() {
        return proverb;
    }

    public String getAuthor() {
        return author;
    }

    // 설정자 메서드 추가
    public void setProverb(String proverb) {
        this.proverb = proverb;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return id + " / " + author + " / " + proverb;
    }
}
