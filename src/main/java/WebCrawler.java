import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

class WebCrawler {

         private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
         private String url;
         private ExecutorService mainPool;
         private String word;
         private String word2;
         private String word3;
         private String word4;


         public WebCrawler(String startingURL,String word,String word2,String word3,String word4, int maxThreads) {
             this.url = startingURL;
             mainPool = Executors.newFixedThreadPool(maxThreads);
             this.word=word;
             this.word2=word2;
             this.word3=word3;
             this.word4=word4;
         }

         private void startNewThread(String link,String word,String word2,String word3,String word4){
        mainPool.execute(new Crawler(link, word,word2,word3,word4));
        }

    @Override
    public String toString() {
        return "WebCrawler{" +
                "word='" + word + '\'' +
                '}';
    }

    private void  startCrawling(){
        startNewThread(this.url,this.word,this.word2,this.word3,this.word4);
        }
         /**
          * @param args the command line arguments
          */
         public static void main(String[] args) throws Exception {
//             Scanner scanner=new Scanner(System.in);
//             String url=scanner.nextLine();
//             String word=scanner.nextLine();
             new WebCrawler("https://en.wikipedia.org/wiki/Elon_Musk","Musk","Tesla","Gigafactory","Elon Mask" ,4).startCrawling();
         }
     }



