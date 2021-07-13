package plus.extvos.builtin.async.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import plus.extvos.builtin.async.dto.AsyncTask;
import plus.extvos.builtin.async.service.AsyncRunnable;
import plus.extvos.builtin.async.service.AsyncTaskContainer;
import plus.extvos.builtin.async.service.AsyncTaskRunner;
import plus.extvos.common.exception.ResultException;

/**
 * @author Mingcai SHEN
 */
@Service
public class AsyncTaskRunnerImpl implements AsyncTaskRunner {
    @Autowired
    private AsyncTaskContainer asyncTaskContainer;

    @Override
    public AsyncTask make(AsyncRunnable runnable) throws ResultException {
        return make(runnable, runnable.getClass().getName());
    }

    @Override
    public AsyncTask make(AsyncRunnable runnable, String subject) throws ResultException {
        AsyncTask t = asyncTaskContainer.make(runnable, subject);
        t.setStatus(1);
        asyncTaskContainer.set(t.getIdentity(), t);
//        start(t);
        return t;
    }

    @Override
    @Async
    public void start(AsyncTask t) {
        try {
            asyncTaskContainer.commitPercentage(t.getIdentity(), 1);
            Object o = t.getRunnable().run(n -> {
                asyncTaskContainer.commitPercentage(t.getIdentity(), n);
            });
            asyncTaskContainer.commitResult(t.getIdentity(), o);
        } catch (RuntimeException e) {
            asyncTaskContainer.commitError(t.getIdentity(), e.getMessage());
        }
    }

    @Override
    public boolean abort(AsyncTask t) {
        return false;
    }
}
