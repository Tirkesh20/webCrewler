import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Crawler implements Runnable {
    private String url;
    private String word;
    private  final AtomicLong DEPTH= new AtomicLong(8L);
    private static final int MAX_PAGES_TO_SEARCH = 10000;
    private final Collection<String> pagesVisited =  Collections.synchronizedSet( new HashSet<>());
    private final  List<String> pagesToVisit = new LinkedList<>();
    private AtomicLong counter= new AtomicLong(0);
    public Crawler(String url,String word) {
       this.url=url;
        this.word=word;
    }
    public Crawler(){

    }
    @Override
    public synchronized void run(){
    search(this.url,this.word);
    }

    public synchronized void search(String url, String searchWord) {
        String baseUrl=url;
        int sum=0;
        LinkFinder linkFinder=new LinkFinder();
        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
                String currentUrl;
                if (this.pagesToVisit.isEmpty()) {
                    currentUrl = url;
                    this.pagesVisited.add(url);
                } else {
                    currentUrl = this.nextUrl();
                    System.out.println(currentUrl);
                }
                try {
                    linkFinder.crawl(currentUrl);
                } catch (Exception e) {
                    continue;
                }
                long success = linkFinder.searchForWord(searchWord);
                if (success > 0) {
                    System.out.println(("**Success** Word " + " " + searchWord + "  found at" + "  " + currentUrl + " ---- " + success));
                    sum += success;
                }
                this.pagesToVisit.addAll(linkFinder.getLinks());

        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)"+"sum"+sum);
    }

    private synchronized String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}



