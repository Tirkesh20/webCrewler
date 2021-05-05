import java.util.LinkedList;

public  interface LinkHandler {

    void queueLink(String link,String word) throws Exception;

    int size();

    boolean visited(String link);

    void addVisited(String link);

}
