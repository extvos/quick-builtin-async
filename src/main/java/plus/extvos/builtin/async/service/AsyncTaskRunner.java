package plus.extvos.builtin.async.service;

import plus.extvos.builtin.async.dto.AsyncTask;
import plus.extvos.common.exception.ResultException;

/**
 * @author Mingcai SHEN
 */
public interface AsyncTaskRunner {
    /**
     * Start a runnable task
     *
     * @param runnable task implemented AsyncRunnable
     * @return a AsyncTask instance
     * @throws ResultException when error
     */
    AsyncTask make(AsyncRunnable runnable) throws ResultException;

    /**
     * Start a runnable task with a subject
     *
     * @param runnable task implemented AsyncRunnable
     * @param subject  of task
     * @return a AsyncTask instance
     * @throws ResultException when error
     */
    AsyncTask make(AsyncRunnable runnable, String subject) throws ResultException;

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
