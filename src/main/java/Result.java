
import java.util.Arrays;


public class Result {
   private long count1,count2,count3,count4;
    private String url;


    public Result(long count1, long count2, long count3, long count4, String url) {
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Result{" +
                "count1=" + count1 +
                ", count2=" + count2 +
                ", count3=" + count3 +
                ", count4=" + count4 +
                ", url='" + url + '\'' +
                '}';
    }

    public long getCount1() {
        return count1;
    }

    public void setCount1(long count1) {
        this.count1 = count1;
    }

    public long getCount2() {
        return count2;
    }

    public void setCount2(long count2) {
        this.count2 = count2;
    }

    public long getCount3() {
        return count3;
    }

    public void setCount3(long count3) {
        this.count3 = count3;
    }

    public long getCount4() {
        return count4;
    }

    public void setCount4(long count4) {
        this.count4 = count4;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
