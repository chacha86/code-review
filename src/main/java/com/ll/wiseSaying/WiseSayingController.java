package com.ll.wiseSaying;

import java.util.List;

import static com.ll.wiseSaying.Status.*;

public class WiseSayingController {
    private static final int DEFAULT_PAGE = 1;
    private static final String KEYWORD_TYPE = "keywordType";
    private static final String KEYWORD = "keyword";
    private static final String PAGE = "page";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String ID = "id";
    private WiseSayingService wiseSayingService;

    public WiseSayingController() {
        wiseSayingService = new WiseSayingService(new WiseSayingJSONRepository());
    }


    public WiseSayingReponse add(final String content, final String author) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);

        return new WiseSayingReponse()
                .recentID(wiseSayingService.addWiseSaying(wiseSaying).getId())
                .status(ADD_SUCCESS);
    }

    public WiseSayingReponse list() {
        return list(new Attribute().add(PAGE, String.valueOf(DEFAULT_PAGE)));
    }

    public WiseSayingReponse list(final Attribute attribute) {
        WiseSayingPage wiseSayingPage;
        Long page = 1L;
        if (attribute.has(PAGE)) {
            if (!attribute.get(PAGE).matches("[0-9.]+")) {
                return new WiseSayingReponse().status(FAILED);
            }
            page = Long.valueOf(attribute.get(PAGE));
        }

        if (attribute.has(KEYWORD_TYPE) && attribute.has(KEYWORD)) {
            // search by author
            if (attribute.get(KEYWORD_TYPE).equals(AUTHOR)) {
                wiseSayingPage = wiseSayingService.findWiseSayingByAuthor(attribute.get(KEYWORD), page);
                return new WiseSayingReponse()
                        .addWiseSayingList(wiseSayingPage.getWiseSayingList())
                        .totalPage(wiseSayingPage.getTotalPage())
                        .currentPage(wiseSayingPage.getCurrentPage())
                        .status(LIST_SUCCESS);
            }
            // search by content
            if (attribute.get(KEYWORD_TYPE).equals(CONTENT)) {
                wiseSayingPage = wiseSayingService.findWiseSayingByContent(attribute.get(KEYWORD), page);
                return new WiseSayingReponse()
                        .addWiseSayingList(wiseSayingPage.getWiseSayingList())
                        .totalPage(wiseSayingPage.getTotalPage())
                        .currentPage(wiseSayingPage.getCurrentPage())
                        .status(LIST_SUCCESS);
            }
        }
        wiseSayingPage = wiseSayingService.findWiseSayingPage(page);
        return new WiseSayingReponse()
                .addWiseSayingList(wiseSayingPage.getWiseSayingList())
                .totalPage(wiseSayingPage.getTotalPage())
                .currentPage(wiseSayingPage.getCurrentPage())
                .status(LIST_SUCCESS);
    }

    public WiseSayingReponse update(final Attribute attribute) {
        String id;
        if (!attribute.has(ID) || !attribute.has(AUTHOR) || !attribute.has(CONTENT)) {
            return new WiseSayingReponse().status(FAILED);
        }
        id = attribute.get(ID);
        if (!id.matches("^\\d+$")) {
            return new WiseSayingReponse().status(FAILED);
        }
        if (idExists(id)) return new WiseSayingReponse()
                .recentID(Long.valueOf(id))
                .status(ID_NOT_FOUND);

        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.setContent(attribute.get(CONTENT));
        wiseSaying.setAuthor(attribute.get(AUTHOR));
        wiseSayingService.updateWiseSaying(Long.valueOf(id), wiseSaying);
        return new WiseSayingReponse()
                .recentID(Long.valueOf(id))
                .status(UPDATE_SUCCESS);
    }

    public WiseSayingReponse delete(final Attribute attribute) {
        String id;
        if (!attribute.has(ID)) {
            return new WiseSayingReponse().status(FAILED);
        }
        id = attribute.get(ID);
        if (!id.matches("^\\d+$")) {
            return new WiseSayingReponse().status(FAILED);
        }
        if (idExists(id)) return new WiseSayingReponse()
                .recentID(Long.valueOf(id))
                .status(ID_NOT_FOUND);
        return new WiseSayingReponse()
                .recentID(wiseSayingService.deleteWiseSaying(Long.valueOf(id)))
                .status(DELETE_SUCCESS);
    }

    private boolean idExists(String id) {
        try {
            wiseSayingService.findWiseSayingByID(Long.valueOf(id));
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public WiseSayingReponse findWiseSayingByID(final Long id) throws Exception {
        return new WiseSayingReponse().addWiseSayingList(List.of(wiseSayingService.findWiseSayingByID(id)));
    }

    public String build() {
        return wiseSayingService.build();
    }
}
