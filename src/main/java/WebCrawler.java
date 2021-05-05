import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

class WebCrawler implements LinkHandler {

         private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
         private String url;
         private ExecutorService mainPool;
         private String word;
         private final int Depth=8;


         public WebCrawler(String startingURL,String word, int maxThreads) {
             this.url = startingURL;
             mainPool = Executors.newFixedThreadPool(maxThreads);;
             this.word=word;
         }

    private void startNewThread(String link,String word) throws Exception {
        mainPool.execute(new Crawler(link, word));
    }

    @Override
    public void queueLink(String link,String word) throws Exception {
        startNewThread(link,word);
    }

        @Override
         public int size() {
             return visitedLinks.size();
         }

         @Override
         public void addVisited(String s) {
             visitedLinks.add(s);
         }


    private void  startCrawling() throws Exception {
        startNewThread(this.url,this.word);
    }
    @Override
         public boolean visited(String s) {
             return visitedLinks.contains(s);
         }

         /**
          * @param args the command line arguments
          */
         public static void main(String[] args) throws Exception {
             new WebCrawler("http://www.pornhub.com","lolly_lips" ,2).startCrawling();
         }
     }



