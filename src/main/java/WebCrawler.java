import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WebCrawler {

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
         public static void main(String[] args){
             Scanner scanner=new Scanner(System.in);
             String url=scanner.nextLine();
             String word=scanner.nextLine();
             String word2=scanner.nextLine();
             String word3=scanner.nextLine();
             String word4=scanner.nextLine();
             new WebCrawler(url,word,word2,word3,word4 ,4).startCrawling();
         }
    }



