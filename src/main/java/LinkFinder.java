import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkFinder {
    private  String url;
    private String searchWord;
    public LinkHandler linkHandler;
    Object object;
    private  Document htmlDocument;
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Collection<String> links = Collections.synchronizedList(new LinkedList<>());
    public LinkFinder(String url,String searchWord) {
        this.url = url;
        this.searchWord=searchWord;
    }
  public LinkFinder(){

  }
    public synchronized boolean crawl(String url){
        try
        {
            CrawlURL currentURl=new CrawlURL(url,new AtomicInteger(0));
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if (this.htmlDocument==null){
                return false;
            }
            if(connection.response().statusCode() == 200)
            {
                System.out.println("\n**Visiting** Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add((link.absUrl("href")));
            }
            return true;
        } catch (Exception e) {
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
