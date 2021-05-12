import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CsvWriter {
    public void toCsv() throws IOException {
            File file=new File("test.csv");
            if(!file.exists()){
                file.createNewFile();
            }
            try (PrintWriter writer = new PrintWriter(file)) {
                StringBuilder sb = new StringBuilder();
                Crawler crawler = new Crawler();
                List notSorted = (List) crawler.getResultNotSorted();
                if (!notSorted.isEmpty()) {
                    String collect = (String) notSorted.stream().collect(Collectors.joining("\n"));
                    sb.append(" not sorted ");
                    sb.append("Url ");
                    sb.append(" ,     ");
                    sb.append("hits");
                    sb.append('\n');
                    for (Object o:notSorted) {
                        writer.write(collect);
                    }
                    List sorted = new LinkedList(notSorted);
                    sorted.sort(new CountsComparator());
                    for (Object o : sorted) {
                        sb.append("sorted");
                        sb.append(o);
                        sb.append('\n');
                    }
                    writer.write(sb.toString());
                }
            } catch(FileNotFoundException e){
                    System.out.println(e.getMessage());
            }
    }
}
