import java.util.List;
import java.util.Optional;

public interface Repository {
  public Integer create(WiseSaying wiseSaying);
  public Optional<WiseSaying> fetch(Integer id);
  public List<WiseSaying> fetchAll();
  public List<WiseSaying> fetchWhereContentContains(String query);
  public List<WiseSaying> fetchWhereAuthorContains(String query);
  public void delete(Integer id);
  public void update(Integer id, String content, String author);
}
