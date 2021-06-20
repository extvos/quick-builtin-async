package org.extvos.builtin.async.service;

import org.extvos.builtin.async.dto.AsyncTask;
import org.extvos.restlet.exception.RestletException;

/**
 * @author Mingcai SHEN
 */
public interface AsyncTaskRunner {
    /**
     * Start a runnable task
     *
     * @param runnable task implemented AsyncRunnable
     * @return a AsyncTask instance
     * @throws RestletException when error
     */
    AsyncTask make(AsyncRunnable runnable) throws RestletException;

    /**
     * Start a runnable task with a subject
     *
     * @param runnable task implemented AsyncRunnable
     * @param subject  of task
     * @return a AsyncTask instance
     * @throws RestletException when error
     */
    AsyncTask make(AsyncRunnable runnable, String subject) throws RestletException;

    /**
     * start the async task
     *
     * @param t AsyncTask instance
     */
    void start(AsyncTask t);

    /**
     * abort the async task
     *
     * @param t AsyncTask instance
     * @return true  if success
     */
    boolean abort(AsyncTask t);
}
