import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

class WebCrawler {

         private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
         private String url;
         private ExecutorService mainPool;
         private String word;
         private final int Depth=8;


         public WebCrawler(String startingURL,String word, int maxThreads) {
             this.url = startingURL;
             mainPool = Executors.newFixedThreadPool(maxThreads);
             this.word=word;
         }

         private void startNewThread(String link,String word){
        mainPool.execute(new Crawler(link, word));
        }


         private void  startCrawling(){
        startNewThread(this.url,this.word);
        }
         /**
          * @param args the command line arguments
          */
         public static void main(String[] args) throws Exception {
             new WebCrawler("https://en.wikipedia.org","Musk" ,4).startCrawling();
         }
     }



