import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvWriter {
    public static void main(String[] args) {

        try (PrintWriter writer = new PrintWriter("test.csv")) {

            StringBuilder sb = new StringBuilder();
            sb.append("id");
            sb.append(',');
            sb.append("url");
            sb.append(" ,");
            sb.append("hits");
            sb.append('\n');
            for( Integer i=1;i<100;i++) {
                sb.append(i.toString());
                sb.append(',');
                sb.append("Prashant Ghimire");
                sb.append('\n');
            }
            writer.write(sb.toString());

            System.out.println("done!");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
