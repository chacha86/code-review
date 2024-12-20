package wiseSaying10;

import java.util.Map;
import org.json.simple.JSONObject;

public class Quote {
    Map<Integer, Quote> quoteMap;
    int id;
    private String quote;
    private String author;

    public Quote(int id, String quote, String author) {
        this.id = id;
        this.quote = quote;
        this.author = author;
    }

    public String getQuote() {
        return this.quote;
    }

    public String getAuthor() {
        return this.author;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("quote", this.quote);
        obj.put("author", this.author);
        return obj;
    }
}