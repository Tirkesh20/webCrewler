import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class HttpUrlChecker {
    private final ReentrantLock lock = new ReentrantLock();
    private ConcurrentHashMap<CrawlerUrl, Long> urlsContainer = new ConcurrentHashMap<>();
    public final Status status=new Status();
    public boolean shouldBeCalled(String url) {
        try {
            lock.lock();
            if (ifAdd(url)) {
                urlsContainer.put(new CrawlerUrl(url), 1L);
                return true;
            }
        } finally {
            lock.unlock();
        }
        return false;
    }


    public boolean ifAdd(String url) {
        for (Map.Entry<CrawlerUrl, Long> m : urlsContainer.entrySet()) {
            CrawlerUrl key = m.getKey();
            if (m.getValue() == 8) {
                status.setStatus(false);
            }
            if (key.isSubUrl(url) && !key.isCalled(url) && m.getValue() < 9) {

                m.getKey().addUrl(url);
                m.setValue(m.getValue() + 1);
                return false;
            }
            if (!key.isSubUrl(url)) {
                return true;
            }
        }
        return true;
    }

    public CrawlerUrl returnFirst() {
        Map.Entry<CrawlerUrl, Long> next = urlsContainer.entrySet().iterator().next();
        if (next != null) {
            return next.getKey();
        }
        return null;
    }
}