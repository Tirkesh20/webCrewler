import java.io.IOException;

public class Status {
    private boolean status=true;

    public void setStatus(boolean status) throws IOException {
        this.status = status;
        if (status==false){
            CsvWriter csvWriter=new CsvWriter();
            csvWriter.toCsv();
        }
    }

    public boolean getStatus() {
        return status;
    }
}
