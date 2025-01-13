package common.global;

public class AppConfig {
    private static String mode;
    private static String dbMode;

    static {
        setDevMode();
        setFileDbMode();
    }

    public static void setProdMode() {
        mode = "prod";
    }

    public static void setDevMode() {
        mode = "dev";
    }

    public static void setTestMode() {
        mode = "test";
    }

    public static boolean isProd() {
        return mode.equals("prod");
    }

    public static boolean isDev() {
        return mode.equals("dev");
    }

    public static boolean isTest() {
        return mode.equals("test");
    }

    public static void setFileDbMode() {
        dbMode = "file";
    }

    public static void setMemDbMode() {
        dbMode = "mem";
    }

    public static boolean isFileDb() {
        return dbMode.equals("file");
    }

    public static boolean isMemDb() {
        return dbMode.equals("mem");
    }

    public static String getDbPath() {
        return "db/" + mode;
    }
}
