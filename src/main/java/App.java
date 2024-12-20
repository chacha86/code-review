import java.util.LinkedHashMap;
import java.util.Map;

public class App {

  private final Controller controller;

  public App() {
    controller = new Controller();
    System.out.println("== WiseSaying ==");
  }
  // TODO: 파싱된 URL parameter sanitize 하기
  public boolean parseCommand(String command) {
    if (command.equals("exit")) {
      return false;
    }
    else if (command.equals("create")) {
      controller.create();
    }
    else if (command.startsWith("list")) {
      if (command.indexOf("?") != -1) {
        controller.getList(parseParams(command));
      }
      else {
        controller.getList(Map.of());
      }
    }
    else if (command.startsWith("get")) {
      controller.get(parseParams(command));
    }
    else if (command.startsWith("update")) {
      controller.update(parseParams(command));
    }
    else if (command.startsWith("delete")) {
      controller.delete(parseParams(command));
    }
    else if (command.equals("build")) {
      controller.build();
    }

    return true;
  }

  private Map<String, String> parseParams(String command) {
    Map<String, String> query_pairs = new LinkedHashMap<>();
    int param_idx = command.indexOf('?');
    String[] pairs = command.substring(param_idx+1).split("&");
    for (String pair: pairs) {
      int delim_idx = pair.indexOf('=');
      query_pairs.put(pair.substring(0, delim_idx), pair.substring(delim_idx+1));
    }

    if (query_pairs.containsKey("page")) {
      int corrected = Math.max(0, Integer.valueOf(query_pairs.get("page")) - 1);
      query_pairs.put("page", String.valueOf(corrected));
    }

    return query_pairs;
  }
}
