package wiseSaying;

public enum Command {
    등록, 목록, 삭제, 수정, 빌드, 종료;

    public static boolean containsCommand(String command) {
        try {
            Command.valueOf(command);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
