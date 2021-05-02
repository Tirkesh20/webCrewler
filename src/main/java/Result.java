public class Result {
    private String word;
    private String url;
    private Integer hints;

    public Result(){

    }
    public Result(String word, String url, Integer hints) {
        this.word = word;
        this.url = url;
        this.hints = hints;
    }
}
