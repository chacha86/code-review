package wiseSaying;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class wiseSayingService {
    public final wiseSayingRepository wiseSayingRepository;

    public wiseSayingService(){
        this.wiseSayingRepository = new wiseSayingRepository();
    }

    public void addSaying(String content, String author) throws IOException {
        int id = wiseSayingRepository.plusLastId();
        wiseSayingRepository.saveContent(id, content, author);
        wiseSayingRepository.saveLastId();
    }

    public void deleteSaying(int number){
        wiseSayingRepository.deleteContent(number);
    }

    public void updateSaying(int id, String content, String author){
        wiseSayingRepository.saveContent(id, content, author);
    }

    public List<WiseSaying> searchSaying(String keywordType, String keyword){
        List<WiseSaying> result = new ArrayList<>();
        Map<Integer, WiseSaying> wiseSayingMap = wiseSayingRepository.getWiseSayings();

        for(WiseSaying wiseSaying : wiseSayingMap.values()){
            if("content".equalsIgnoreCase(keywordType) && wiseSaying.getContent().contains(keyword)){
                result.add(wiseSaying);
            }
            else if("author".equalsIgnoreCase(keywordType) && wiseSaying.getAuthor().contains(keyword)){
                result.add(wiseSaying);
            }
        }

        return result;
    }

    public List<WiseSaying> getPageWiseSayings(int page){
        final int pageSize = 5;
        Map<Integer, WiseSaying> wiseSayingMap = wiseSayingRepository.getWiseSayings();

        List<WiseSaying> sayingList = new ArrayList<>(wiseSayingMap.values());
        sayingList.sort((a, b) -> Integer.compare(b.getId(), a.getId()));

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, sayingList.size());

        if(start >= sayingList.size()){
            return Collections.emptyList();
        }

        return sayingList.subList(start, end);
    }

    public int getMaxPage(){
        final int pageSize = 5;

        int MaxPage = wiseSayingRepository.getWiseSayings().size() / 5;
        if(wiseSayingRepository.getWiseSayings().size() % 5 != 0)
            MaxPage++;

        return MaxPage;
    }

    public Map<Integer, WiseSaying> getWiseSayings(){
        return wiseSayingRepository.getWiseSayings();
    }

    public boolean contentExists(int id) {
        return wiseSayingRepository.contentExists(id);
    }

    public void build(){
        wiseSayingRepository.buildJson();
    }

    public int getLastId(){
        return wiseSayingRepository.getLastId();
    }
}
