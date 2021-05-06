import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkFinder {
    private String url;
    private String searchWord;
    private Document htmlDocument;
    private Long counter;

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Collection<String> links = Collections.synchronizedList(new LinkedList<>());
    private HttpUrlChecker httpUrlChecker = new HttpUrlChecker();

    public synchronized boolean crawl(String url) {
        try {
            CrawlerUrl crawlerUrl = new CrawlerUrl(url);
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if (this.htmlDocument == null) {
                return false;
            }
            if (connection.response().statusCode() == 200) {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure*");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for (Element link : linksOnPage) {
//                System.out.println(link.attr("href"));
                if (httpUrlChecker.shouldBeCalled(link.attr("href"))){
                    this.links.add(link.absUrl("href"));
                }
            }

            return true;
        }catch( Exception e) {
        e.printStackTrace();
        return false;
    }
}

    public synchronized long searchForWord(String searchWord) {
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return 0;
        }
        String bodyText = this.htmlDocument.body().text();
        Matcher matcher = Pattern.compile("\\b+"+searchWord+"\\b", Pattern.CASE_INSENSITIVE).matcher(bodyText);
        long matches = matcher.results().count();
        return matches;
    }
    public synchronized Collection<String> getLinks()
    {
        return this.links;
    }

}
