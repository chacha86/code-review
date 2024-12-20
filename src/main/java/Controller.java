import java.util.List;
import java.util.Map;
import java.util.Scanner;

// TODO: View 레이어의 부재로 관련 로직이 controller에 들어있는데, 이것을 App으로 이관시킬지 고려
public class Controller {

  private final Service service;
  private final Scanner in;
  private final Integer PAGE_SIZE = 5;
  private final Integer PAGINATION_RANGE = 5;

  public Controller() {
    service = new Service();
    in = new Scanner(System.in);
  }

  public void create() {
    System.out.print("Content : ");
    String content = in.nextLine();
    System.out.print("Author : ");
    String author = in.nextLine();
    Integer id = service.add(content, author);
    System.out.println("Added entity with id " + id + ".");
  }

  public void get(Map<String, String> params) {
    Integer id = Integer.valueOf(params.get("id"));
    service.get(id).ifPresentOrElse(result -> {
          System.out.println(result.getId() + " / " + result.getAuthor() + " / " + result.getContent());
        },
        () -> System.out.println("Entity with id " + id + " does not exist."));
  }

  public void getList(Map<String, String> params) {
    List<WiseSaying> results = service.getList(params);
    int lo = 0;
    int hi = Math.min(results.size(), PAGE_SIZE);
    Integer currentPage = 0;
    if (params.containsKey("page")) {
      int offset = Integer.valueOf(params.get("page")) * PAGE_SIZE;
      lo = Math.min(results.size(), offset);
      hi = Math.min(results.size(), offset + PAGE_SIZE);
      currentPage = Integer.valueOf(params.get("page"));
    }
    String paginationBar = generatePaginationBar(results.size(), currentPage);
    results = results.subList(lo, hi);

    System.out.println("Number / Author / Saying");
    System.out.println("-------------------------");
    for(WiseSaying result: results) {
      System.out.println(result.getId() + " / " + result.getAuthor() + " / " + result.getContent());
    }
    System.out.println(paginationBar);
  }

  public void update(Map<String, String> params) {
    Integer id = Integer.valueOf(params.get("id"));
    service.get(id).ifPresentOrElse(result -> {
          System.out.println("Content(original) : " + result.getContent());
          System.out.print("Content : ");
          String new_content = in.nextLine();
          System.out.println("Author(original) : " + result.getAuthor());
          System.out.print("Author : ");
          String new_author = in.nextLine();
          service.update(id, new_content, new_author);
        },
        () -> System.out.println("Entity with id " + id + " does not exist."));
  }

  public void delete(Map<String, String> params) {
    Integer id = Integer.valueOf(params.get("id"));
    service.remove(id);
    System.out.println("Removed entity with id " + id + ".");
  }

  public void build() {
    service.build();
    System.out.println("Refreshed content of data.json.");
  }

  private String generatePaginationBar(Integer listLength, Integer pageNumber) {
    int maxPage = listLength / PAGE_SIZE + (listLength % PAGE_SIZE != 0 ? 1 : 0) - 1;
    String res = "Pages : ";
    int lo = Math.max(0, pageNumber - PAGINATION_RANGE / 2) ;
    int hi = Math.min(maxPage, pageNumber + PAGINATION_RANGE / 2);
    for (int i = lo; i <= hi; i++) {
      String pageStr = i == pageNumber ? "[" + (i+1) + "]" : String.valueOf(i+1);
      res += pageStr + (i == hi ? "" : " / ");
    }

    return res;
  }
}
