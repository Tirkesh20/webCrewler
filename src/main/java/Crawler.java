import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Crawler implements Runnable {
    private String url;
    private String word;
    private String word2;
    private String word3;
    private String word4;
    private static final int MAX_PAGES_TO_SEARCH = 10000;
    private final Collection<Result> resultNotSorted=Collections.synchronizedList(new LinkedList<>());
    private final Collection<Result> resultSorted=Collections.synchronizedList(new LinkedList<>());
    private final Collection<String> pagesVisited =  Collections.synchronizedSet( new HashSet<>());
    private final  List<String> pagesToVisit = new LinkedList<>();
    public Crawler(String url,String word,String word2,String word3,String word4) {
       this.url=url;
        this.word=word;
        this.word2=word2;
        this.word3=word3;
        this.word4=word4;
    }
public Crawler(){

}
    @Override
    public synchronized void run(){
    search(this.url,this.word,this.word2,this.word3,this.word4);
    }

    public synchronized void search(String url, String word,String word2,String word3,String word4) {
        Status status=new Status();
        LinkFinder linkFinder=new LinkFinder();
        while (status.getStatus()) {
            if (pagesVisited.size()==MAX_PAGES_TO_SEARCH){
                return;
            }
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
                long[] success = linkFinder.searchForWord(word,word2,word3,word4);
                System.out.println(("**Success** Word " + " " +success[0]+" "+success[1]+" "+success[2]+" "+success[3]+" found at" + "  " + currentUrl + " ---- "));
                Result result=new Result(success,currentUrl);
            resultNotSorted.add(result);
            this.pagesToVisit.addAll(linkFinder.getLinks());
            System.out.println(status.getStatus());
            System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
        }
    }

    public Collection getResultNotSorted(){
        return this.resultNotSorted;
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



