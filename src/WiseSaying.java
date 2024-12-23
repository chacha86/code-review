public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toJson() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"content\": \"" + content + "\",\n" +
                "  \"author\": \"" + author + "\"\n" +
                "}";
    }

    public static WiseSaying fromJson(String json) {
        int id = Integer.parseInt(extractValue(json, "\"id\":"));
        String content = extractValue(json, "\"content\":");
        String author = extractValue(json, "\"author\":");

        return new WiseSaying(id, content, author);
    }

    private static String extractValue(String json, String key) {
        int startIndex = json.indexOf(key);
        if (startIndex == -1) {
            throw new IllegalArgumentException("키를 찾을 수 없음: " + key);
        }

        startIndex = json.indexOf(":", startIndex) + 1;
        int endIndex = json.indexOf(",", startIndex);

        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }

        String value = json.substring(startIndex, endIndex).trim();

        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }
}
