class Wise {
    private String wise;
    private String author;
    private int id; // 인스턴스별 id 값
    private static int idCounter = 0; // 고유 id 값을 생성하기 위한 static 변수

    public Wise(String wise, String author) {
        this.wise = wise;
        this.author = author;
        this.id = ++idCounter; // 고유 id 값을 부여
    }

    public String getWise() {
        return wise;
    }

    public void setWise(String wise) {
        this.wise = wise;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }
}
