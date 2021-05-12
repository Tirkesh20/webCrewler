import java.util.Comparator;

public class CountsComparator implements Comparator<Result> {
    @Override
    public int compare(Result o1, Result o2) {
        return Long.compare(o2.getCount1(), o1.getCount2());
    }
}

