package wiseSaying;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WiseSaying {
    String cmd;
    String wise;
    String author;
    String wiseAuthorSet;
    String removeKey;
    String updateKey;
    String parseMapValue;
    int lastId = 0;
}
