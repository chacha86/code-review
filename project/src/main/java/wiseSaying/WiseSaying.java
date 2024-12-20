package wiseSaying;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WiseSaying {
    private static Long sequence = 0L;

    private Long id;
    private String content;
    private String author;

    public static WiseSaying createWise(String content, String author) {
        return new WiseSaying(++sequence, content, author);
    }
}
