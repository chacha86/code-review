package org.example;

class WiseSaying
{
    private String quote;
    private String author;
    private int id;

    public WiseSaying(int id, String quote, String author) {
        this.id = id;
        this.quote = quote + " " + id;
        this.author = author + " " + id;
    }

    public WiseSaying() {
    }

    public String getQuote() {
        return quote;

    }
    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {

        int lastIndexQuote = quote.lastIndexOf(" ");
        int lastIndexAuthor = author.lastIndexOf(" ");

        String quote = this.quote.substring(0,lastIndexQuote);
        String author = this.author.substring(0,lastIndexAuthor);

        return id + " / " + author + " / " + quote;

    }
}