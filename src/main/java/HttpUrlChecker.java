import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class HttpUrlChecker {
    private final ReentrantLock lock = new ReentrantLock();
    private ConcurrentHashMap<CrawlerUrl, Long> urlsContainer = new ConcurrentHashMap<>();

    public boolean shouldBeCalled(String url) {
        try {
            lock.lock();
            if (ifAdd(url)) {
                urlsContainer.put(new CrawlerUrl(url), 1L);
            }
        } finally {
            lock.unlock();
        }
        return false;
    }


    public boolean ifAdd(String url) {
        for (Map.Entry<CrawlerUrl, Long> m : urlsContainer.entrySet()) {
            CrawlerUrl key = m.getKey();
            if (key.isSubUrl(url) && !key.isCalled(url) && m.getValue() < 9) {
                m.getKey().addUrl(url);
                m.setValue(m.getValue() + 1);
                System.out.println(url);
                return false;
            }
            if (!key.isSubUrl(url)) {
                return  true;
            }
        }
        return true;
    }
}