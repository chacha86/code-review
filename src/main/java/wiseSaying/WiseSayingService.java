package wiseSaying;

public class WiseSayingService {

    WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();


    public void insertWise(WiseSaying ws){
        wiseSayingRepository.insertWise(ws);
    }

    public void listWise(WiseSaying ws){
        wiseSayingRepository.listWise(ws);
    }

    public void deleteWise(WiseSaying ws){
        wiseSayingRepository.deleteWise(ws);
    }

    public void updateWise(WiseSaying ws){
        wiseSayingRepository.updateWise(ws);
    }

    public void buildWise(WiseSaying ws){
        wiseSayingRepository.buildWise(ws);
    }

}
