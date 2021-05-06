import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;


public class SingletonCounter {
    private final AtomicLong counter;
    private static final ReentrantLock lock = new ReentrantLock();
    private static SingletonCounter instance = null;

    private SingletonCounter(Long init) {
        counter = new AtomicLong(init);
    }

    public static Long getIndex() { // #3
        try {
            lock.lock();
            if (instance == null) {
                instance = new SingletonCounter(0L);
            }
            return instance.counter.incrementAndGet();
        } finally {
            lock.unlock();
        }
    }
}
