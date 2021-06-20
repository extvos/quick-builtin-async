package org.extvos.builtin.async.service.impl;

import org.extvos.builtin.async.dto.AsyncTask;
import org.extvos.builtin.async.service.AsyncRunnable;
import org.extvos.builtin.async.service.AsyncTaskContainer;
import org.extvos.builtin.async.service.AsyncTaskRunner;
import org.extvos.restlet.exception.RestletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Mingcai SHEN
 */
@Service
public class AsyncTaskRunnerImpl implements AsyncTaskRunner {
    @Autowired
    private AsyncTaskContainer asyncTaskContainer;

    @Override
    public AsyncTask make(AsyncRunnable runnable) throws RestletException {
        return make(runnable, runnable.getClass().getName());
    }

    @Override
    public AsyncTask make(AsyncRunnable runnable, String subject) throws RestletException {
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
