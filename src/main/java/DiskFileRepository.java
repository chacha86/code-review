import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 디스크에 저장된 파일은 외부에서 접근이 불가능하다는 전제로 구현
public class DiskFileRepository implements Repository {
  private final String BASE_FILEPATH = "db" + File.separator + "wiseSaying";
  private final String PATH_TO_LAST_ID_FILE = BASE_FILEPATH + "/lastId.txt";
  private final String ENTITY_EXT = ".json";

  private final ObjectMapper mapper;
  private final Map<Integer, WiseSaying> entities;
  private File base_path;
  private File last_id_path = new File(PATH_TO_LAST_ID_FILE);
  private Integer last_id;

  public DiskFileRepository() {
    mapper = new ObjectMapper();
    entities = new HashMap<>();

    // 디렉토리가 존재하지 않으면 생성
    base_path = new File(BASE_FILEPATH);
    if(base_path.exists() == false) {
      try {
        base_path.mkdirs();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }

    // lastId.txt를 읽어들이고, 존재하지 않을 시 생성
    File last_id_file = new File(PATH_TO_LAST_ID_FILE);
    try {
      if (last_id_file.createNewFile() == true) {
        last_id = 0;
        updateLastId();
      }
      else {
        Scanner fr = new Scanner(last_id_file);
        last_id = fr.nextInt();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    // 영속 디렉토리 내 .json 파일을 WiseSaying 오브젝트로 역직렬화(deserialize)
    String[] files = base_path.list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.toLowerCase().endsWith(ENTITY_EXT);
      }});

    for(String file: files) {
      try {
        WiseSaying entity = mapper.readValue(new File(BASE_FILEPATH + File.separator + file), WiseSaying.class);
        entities.put(entity.getId(), entity);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Integer create(WiseSaying wiseSaying) {
    last_id++;
    updateLastId();
    wiseSaying.setId(last_id);
    File entity = new File(BASE_FILEPATH + "/" + last_id + ENTITY_EXT);
    entities.put(wiseSaying.getId(), wiseSaying);
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(entity, wiseSaying);
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return wiseSaying.getId();
  }

  @Override
  public Optional<WiseSaying> fetch(Integer id) {
    if(entities.containsKey(id) == false) {
      return null;
    }
    return Optional.ofNullable(entities.get(id));
  }


  @Override
  public List<WiseSaying> fetchAll() {
    return entities.values().stream()
        .sorted((a, b) -> b.getId().compareTo(a.getId()))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public List<WiseSaying> fetchWhereContentContains(String query) {
    return entities.values().stream()
        .filter(e -> e.getContent().contains(query))
        .sorted((a, b) -> b.getId().compareTo(a.getId()))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public List<WiseSaying> fetchWhereAuthorContains(String query) {
    return entities.values().stream()
        .filter(e -> e.getAuthor().contains(query))
        .sorted((a, b) -> b.getId().compareTo(a.getId()))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public void delete(Integer id) {
    fetch(id).ifPresent(result -> {
      File target = new File(BASE_FILEPATH + "/" + result.getId() + ENTITY_EXT);
      target.delete();
      entities.remove(result.getId());
    });
  }

  @Override
  public void update(Integer id, String content, String author) {
    fetch(id).ifPresent(result -> {
      result.setContent(content);
      result.setAuthor(author);
      File entity = new File(BASE_FILEPATH + "/" + result.getId() + ENTITY_EXT);
      try {
        mapper.writeValue(entity, result);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
  // lastId.txt를 현재의 last_id값으로 최신화
  private void updateLastId() {
    try {
      FileWriter f = new FileWriter(last_id_path);
      f.write(last_id.toString());
      f.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
