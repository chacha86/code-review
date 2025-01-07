package com.ll.wiseSaying;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandLineMenu {
    private final String APP_WELCOME_GREETING = "== 명언 앱 ==";
    private final String CMD_INPUT_INFO = "명령) ";
    private final String CONTENT_INPUT_INFO = "명언 : ";
    private final String AUTHOR_INPUT_INFO = "작가 : ";
    private final String EXIT_INFO = "종료합니다.";
    private final String INVALID_CMD_INFO = "잘못된 입력입니다.";
    private final String ADDED_INFO = "번 명언이 등록되었습니다.";
    private final String FAILED_INFO = "작업을 실패했습니다.";
    private final String ID_NOT_FOUND_INFO = "번 명언은 존재하지 않습니다.";
    private final String DELETED_INFO = "번 명언이 삭제되었습니다.";
    private final String LIST_INFO_FORE = "번호 / 작가 / 명언\n";
    private final String BOUNDARY_LINE = "----------------------";
    private final String OLD_CONTENT_INFO = "명언(기존) : ";
    private final String OLD_AUTHOR_INFO = "작가(기존) : ";
    private final String BUILD_COMPLETE_INFO = " 파일의 내용이 갱신되었습니다.";

    private BufferedReader bufferedReader;

    public CommandLineMenu() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void printWelcomeGreeting() {
        System.out.println(APP_WELCOME_GREETING);
    }

    public void printCmdInputInfo() {
        System.out.print(CMD_INPUT_INFO);
    }

    public String cmdInput() throws IOException {
        return bufferedReader.readLine();
    }

    public String inputContent() throws IOException {
        System.out.print(CONTENT_INPUT_INFO);
        return bufferedReader.readLine();
    }

    public String inputAuthor() throws IOException {
        System.out.print(AUTHOR_INPUT_INFO);
        return bufferedReader.readLine();
    }

    public void printInvalidCmdInfo() {
        System.out.println(INVALID_CMD_INFO);
    }

    public void printExitInfo() {
        System.out.println(EXIT_INFO);
    }

    public void printAdddedInfo(Long id) {
        System.out.println(id + ADDED_INFO);
    }

    public void printFailedInfo() {
        System.out.println(FAILED_INFO);
    }

    public void printIDNotFoundInfo(final Long id) {
        System.out.println(id + ID_NOT_FOUND_INFO);
    }

    public void printDeletedInfo(final Long id) {
        System.out.println(id + DELETED_INFO);
    }

    public void printWiseSayingList(final List<WiseSaying> wiseSayingList, final Long currentPage, final Long maxPage) {
        System.out.println(LIST_INFO_FORE + BOUNDARY_LINE);
        for (WiseSaying wiseSaying : wiseSayingList) {
            System.out.println(
                    wiseSaying.getId() + " / " +
                    wiseSaying.getAuthor() + " / " +
                    wiseSaying.getContent()
            );
        }
        System.out.println(BOUNDARY_LINE + "\n페이지 : " + currentPage + " / [" + maxPage + "]");
    }

    public void printOldContent(String oldContent) {
        System.out.println(OLD_CONTENT_INFO + oldContent);
    }

    public void printOldAuthor(String oldAuthor) {
        System.out.println(OLD_AUTHOR_INFO + oldAuthor);
    }

    public void printBuildCompleteInfo(final String buildFilename) {
        System.out.println(buildFilename + BUILD_COMPLETE_INFO);
    }
}
