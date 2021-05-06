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

    private Document htmlDocument;

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private Collection<String> links = Collections.synchronizedList(new LinkedList<>());
    private HttpUrlChecker httpUrlChecker = new HttpUrlChecker();

    public synchronized boolean crawl(String url) {
        try {
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

    public synchronized long[] searchForWord(String searchWord,String word2,String word3,String word4) {
        long[] res=new long[4];
        if(this.htmlDocument == null)
        {
            System.out.println("ERROR! Call crawl() before performing analysis on the document");
            return null;
        }
        String bodyText = this.htmlDocument.body().text();
        Matcher matcher = Pattern.compile("\\b+"+searchWord+"\\b", Pattern.CASE_INSENSITIVE).matcher(bodyText);
        Matcher matcher2 = Pattern.compile("\\b+"+word2+"\\b", Pattern.CASE_INSENSITIVE).matcher(bodyText);
        Matcher matcher3 = Pattern.compile("\\b+"+word3+"\\b", Pattern.CASE_INSENSITIVE).matcher(bodyText);
        Matcher matcher4 = Pattern.compile("\\b+"+word4+"\\b", Pattern.CASE_INSENSITIVE).matcher(bodyText);
            res[0] = matcher.results().count();
        res[1]=matcher2.results().count();
        res[2]=matcher3.results().count();
        res[3]=matcher4.results().count();
        return res;
    }
    public synchronized Collection<String> getLinks()
    {
        return this.links;
    }

}
