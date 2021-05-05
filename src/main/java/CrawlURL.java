import java.util.concurrent.atomic.AtomicInteger;

public class CrawlURL {
    public String url;
    public AtomicInteger depth;

    public CrawlURL(String url, AtomicInteger depth) {
        this.url = url;
        this.depth = depth;
    }
}
