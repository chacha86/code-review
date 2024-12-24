package spring;

public class WiseSaying {

    int     number;   // 순서 번호
    String content;   // 명언 내용
    String  author;   // 작가

    public WiseSaying() { }

    public WiseSaying(int number, String content, String author) {
        this.number = number;
        this.content = content;
        this.author = author;
    }
    // Getter
    public int getNumber() { return number; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }

    // Setter
    public void setNumber(int number) { this.number = number; }
    public void setContent(String content) { this.content = content; }
    public void setAuthor(String author) { this.author = author; }
    
    // 출력용 
    public String formatPrint() {
        return number + " / " + content + " / " + author;
    }
} 