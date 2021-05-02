import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {

    private static final int DEPTH=8;
    private static final int MAX_PAGES_TO_SEARCH = 100;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    public void search(String url, String searchWord) {
        int sum=0;
        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl);
            long success = leg.searchForWord(searchWord);
            if (success>0){
                System.out.println(("**Success** Word "+" "+searchWord+"  found at"+"  "+ currentUrl+" ---- "+success));
                sum+=success;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)"+"sum"+sum);
    }

    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}



