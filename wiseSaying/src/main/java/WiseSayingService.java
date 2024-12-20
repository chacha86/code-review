import java.util.List;

public class WiseSayingService {

    private final WiseSayingRepository repository;

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public int addOne(String content, String author) {
        return repository.save(new WiseSaying(content, author));
    }

    public List<WiseSaying> getAll() {
        return repository.findMany();
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
        List<WiseSaying> wiseSayings = repository.findMany();
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

    public List<WiseSaying> searchWithContent(String keyword) {
        return repository.findWhere("content", keyword);
    }

    public List<WiseSaying> searchWithAuthor(String keyword) {
        return repository.findWhere("author", keyword);
    }
}
