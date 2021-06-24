package plus.extvos.builtin.async.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.extvos.builtin.async.dto.AsyncTask;
import plus.extvos.builtin.async.service.AsyncRunnable;
import plus.extvos.builtin.async.service.AsyncTaskContainer;
import plus.extvos.restlet.Assert;
import plus.extvos.restlet.exception.RestletException;

import java.time.Duration;

/**
 * @author Mingcai SHEN
 */
public class DefaultAsyncTaskContainerImpl implements AsyncTaskContainer {

    private static final Logger log = LoggerFactory.getLogger(DefaultAsyncTaskContainerImpl.class);

    private Cache<String, AsyncTask> taskCache;
    private Long taskCounter;

    public DefaultAsyncTaskContainerImpl() {
        taskCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(15L))
            .maximumSize(1000L)
            .initialCapacity(100)
            .build();
        taskCounter = 0L;
    }

    @Override
    public AsyncTask make(AsyncRunnable r, String subject) throws RestletException {
        taskCounter += 1;
        log.debug("make:> make new task: {}", taskCounter);
        return new AsyncTask(taskCounter.toString(), r, subject);
    }

    @Override
    public AsyncTask get(String identity) throws RestletException {
        AsyncTask t = taskCache.getIfPresent(identity);
        if (null == t) {
            throw RestletException.notFound("task with identity '" + identity + "' not exists");
        }
        return t;
    }

    @Override
    public void set(String identity, AsyncTask t) throws RestletException {
        taskCache.put(identity, t);
    }

    @Override
    public void delete(String identity) throws RestletException {
        taskCache.invalidate(identity);
    }

    @Override
    public void commitPercentage(String identity, int percentage) throws RestletException {
        log.debug("commitPercentage:> {} -> {}", identity, percentage);
        Assert.between(percentage, 0, 101, RestletException.conflict("invalid percentage"));
        AsyncTask t = get(identity);
        t.setStatus(1);
        t.setPercentage(percentage);
        taskCache.put(identity, t);

    }

    @Override
    public void commitError(String identity, String error) throws RestletException {
        log.debug("commitError:> {} -> {}", identity, error);
        AsyncTask t = get(identity);
        t.setStatus(-1);
        t.setError(error);
        taskCache.put(identity, t);
    }

    @Override
    public void commitResult(String identity, Object result) throws RestletException {
        log.debug("commitResult:> {} -> {}", identity, result);
        AsyncTask t = get(identity);
        t.setStatus(2);
        t.setPercentage(100);
        t.setResult(result);
        taskCache.put(identity, t);
    }
}
