import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository repository;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public int addOne(String content, String author) {
        return repository.save(new WiseSaying(content, author));
    }

    public void getAll(int page) {
        List<WiseSaying> wiseSayingList = repository.findMany();
        paging(wiseSayingList, page);
    }

    public boolean deleteOne(int id) {
        return repository.deleteOneById(id);
    }

    public WiseSaying getOne(int id) {
        return repository.findOneById(id);
    }

    public boolean updateOne(WiseSaying wiseSaying) {
        return repository.updateOne(wiseSaying);
    }

    public void build() {
        List<WiseSaying> wiseSayings = repository.findMany()
                .stream().sorted(Comparator.comparingInt(WiseSaying::getId)).toList();
        StringBuilder jsonBuilder = new StringBuilder("[\n");
        for (int i = 0; i < wiseSayings.size(); i++) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            String[] jsonLines = wiseSaying.toJson().split("\n");

            for (int j = 0; j < jsonLines.length; j++) {
                jsonBuilder.append("\t").append(jsonLines[j]);
                if (j < jsonLines.length - 1) jsonBuilder.append("\n");
            }

            if (i < wiseSayings.size() - 1) jsonBuilder.append(",\n");
            else jsonBuilder.append("\n");
        }
        jsonBuilder.append("]");

        repository.saveData(jsonBuilder.toString());
    }

    public void searchWithContent(String keyword, int page) {
        List<WiseSaying> wiseSayingList = repository.findWhere("content", keyword);
        paging(wiseSayingList, page);
    }

    public void searchWithAuthor(String keyword, int page) {
        List<WiseSaying> wiseSayingList =  repository.findWhere("author", keyword);
        paging(wiseSayingList, page);
    }

    public void paging(List<WiseSaying> list, int page) {
        int size = list.size();
        int offset = (page - 1) * 5;
        if (offset < 0 || offset > size - 1) {
            System.out.println("요청하신 페이지에 데이터가 없습니다.");
            return;
        }
        list.subList(offset, Math.min(offset + 5, size)).forEach(System.out::println);
        System.out.println("----------------------");
        System.out.print("페이지 : ");
        int pageSize = (int) Math.ceil((double)size / 5);
        for (int i = 1; i < pageSize + 1; i++) {
            if (i == page) {
                System.out.print("[" + i + "]");
            } else {
                System.out.print(i);
            }
            if (i < pageSize) System.out.print(" / ");
        }
        System.out.print("\n");
    }
}
