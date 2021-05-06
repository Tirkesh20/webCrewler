import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrawlerUrl {
    private Long id;
    private String rootUrl;
    private List<String> subUrls;

    public CrawlerUrl(String rootUrl) {
        this.subUrls = new ArrayList<>();
        this.rootUrl = rootUrl;
        this.id = SingletonCounter.getIndex();
    }

    public boolean isSubUrl (String url){
        String[] split = url.split("/");
        String givenRoot = split.length > 1 ? split[1] : split[0];
        boolean contains = url.contains(givenRoot);
        return contains;
    }

    public boolean isCalled(String url){
            return subUrls.contains(url);
    };

    public void addUrl(String url) {
        subUrls.add(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawlerUrl that = (CrawlerUrl) o;
        return Objects.equals(rootUrl, that.rootUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootUrl);
    }

    public List returnSubs(){
      return this.subUrls;
    }
}

