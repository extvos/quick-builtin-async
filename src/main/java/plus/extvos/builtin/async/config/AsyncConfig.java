package plus.extvos.builtin.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mingcai SHEN
 */
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Value("${quick.builtin.async.core-pool-size:5}")
    private int corePoolSize;

    @Value("${quick.builtin.async.max-pool-size:10}")
    private int maxPoolSize;

    @Value("${quick.builtin.async.queue-capacity:100}")
    private int queueCapacity;

    @Value("${quick.builtin.async.keep-alive-time:100}")
    private long keepAliveTime;
    @Value("${quick.builtin.async.thread-name:}")
    private String threadName;

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Override
    public Executor getAsyncExecutor() {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(queueCapacity);
        AsyncThreadFactory atf;
        if (threadName != null && !threadName.isEmpty()) {
            atf = new AsyncThreadFactory(threadName);
        } else {
            atf = new AsyncThreadFactory();
        }
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS, workQueue, atf);
    }

    public static class AsyncThreadFactory implements ThreadFactory {

        private final AtomicInteger count;
        private final String threadName;

        public AsyncThreadFactory() {
            count = new AtomicInteger(0);
            threadName = AsyncConfig.class.getSimpleName();
        }

        public AsyncThreadFactory(String s) {
            count = new AtomicInteger(0);
            threadName = s;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String name = this.threadName + count.addAndGet(1);
            t.setName(name);
            log.info("newThread:> Created new thread: {} ...", name);
            return t;
        }
    }
}
