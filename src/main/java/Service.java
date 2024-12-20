import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Service {
  private final String BUILD_DATA_FILE = "data.json";
  private final Repository repository;
  private final ObjectMapper mapper;

  public Service() {
    // repository = new InMemoryRepository();
    repository = new DiskFileRepository();
    mapper = new ObjectMapper();
  }

  public Integer add(String content, String author) {
    return repository.create(new WiseSaying(content, author));
  }

  public Optional<WiseSaying> get(Integer id) {
    return repository.fetch(id);
  }

  public List<WiseSaying> getList(Map<String, String> params) {
    List<WiseSaying> results = List.of();

    if (params.containsKey("keywordType") && params.containsKey("keyword")) {
      System.out.println("-------------------------");
      System.out.println("Search type : " + params.get("keywordType"));
      System.out.println("Keyword : " + params.get("keyword"));
      System.out.println("-------------------------");

      switch (params.get("keywordType")) {
        case "content":
          results = repository.fetchWhereContentContains(params.get("keyword"));
          break;
        case "author":
          results = repository.fetchWhereAuthorContains(params.get("keyword"));
          break;
      }
    }
    else {
      results = repository.fetchAll();
    }

    return results;
  }

  public void remove(Integer id) {
    repository.delete(id);
  }

  public void update(Integer id, String content, String author) {
    repository.update(id, content, author);
  }

  public void build() {
    List<WiseSaying> entities = repository.fetchAll();
    File data = new File(BUILD_DATA_FILE);
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(data, entities);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
