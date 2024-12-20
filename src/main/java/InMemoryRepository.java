import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRepository implements Repository {
  private final Map<Integer, WiseSaying> entities;
  private Integer last_int;

  public InMemoryRepository() {
    entities = new HashMap<>();
    last_int = 1;
  }

  @Override
  public Integer create(WiseSaying wiseSaying) {
    wiseSaying.setId(last_int);
    entities.put(wiseSaying.getId(), wiseSaying);
    last_int++;
    return wiseSaying.getId();
  }

  @Override
  public Optional<WiseSaying> fetch(Integer id) {
    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public List<WiseSaying> fetchAll() {
    return new ArrayList<>(entities.values());
  }

  @Override
  public List<WiseSaying> fetchWhereContentContains(String query) {
    return entities.values().stream()
        .filter(e -> e.getContent().contains(query))
        .collect(Collectors.toList());
  }

  @Override
  public List<WiseSaying> fetchWhereAuthorContains(String query) {
    return entities.values().stream()
        .filter(e -> e.getAuthor().contains(query))
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    entities.remove(id);
  }

  @Override
  public void update(Integer id, String content, String author) {
    fetch(id).ifPresent(wiseSaying -> {
      wiseSaying.setContent(content);
      wiseSaying.setAuthor(author);
    });
  }
}
