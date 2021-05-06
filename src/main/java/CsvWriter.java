import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CsvWriter {
    public void toCsv(){
        try (PrintWriter writer = new PrintWriter("test.csv")) {

            StringBuilder sb = new StringBuilder();
            Crawler crawler=new Crawler();
            List notSorted= (List) crawler.getResultNotSorted();
            sb.append(" not sorted");
            sb.append("url");
            sb.append(" ,      ");
            sb.append("hits");
            sb.append('\n');
            Iterator iterator=notSorted.iterator();
            while(iterator.hasNext()){
                sb.append(iterator.next());
                sb.append(',');

                sb.append('\n');
            }
            List  sorted=new LinkedList();
            sorted.addAll(notSorted);
            Collections.sort(notSorted);
            Iterator iterator1=sorted.iterator();
            while(iterator1.hasNext()) {
                sb.append("sorted");
                sb.append(iterator1.next());
            }
            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
