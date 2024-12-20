import lombok.*;

@ToString
@Getter
@Setter
public class WiseSaying {
  private Integer id;
  private String content;
  private String author;

  public WiseSaying() {
  }

  public WiseSaying(String content, String author) {
    this.content = content;
    this.author = author;
  }
}
