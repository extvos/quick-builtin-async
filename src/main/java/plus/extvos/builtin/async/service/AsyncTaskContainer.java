package plus.extvos.builtin.async.service;

import plus.extvos.builtin.async.dto.AsyncTask;
import plus.extvos.common.exception.ResultException;

/**
 * @author Mingcai SHEN
 */
public interface AsyncTaskContainer {
    /**
     * Make a new AsyncTask
     *
     * @param r       AsyncRunnable instance
     * @param subject of task
     * @return AsyncTask instance
     * @throws ResultException when error
     */
    AsyncTask make(AsyncRunnable r, String subject) throws ResultException;

    /**
     * Get AsyncTask by id
     *
     * @param identity of string
     * @return AsyncTask instance
     * @throws ResultException when error
     */
    AsyncTask get(String identity) throws ResultException;

    /**
     * Set AsyncTask
     *
     * @param identity of string
     * @param t        AsyncTask instance
     * @throws ResultException when error
     */
    void set(String identity, AsyncTask t) throws ResultException;

    /**
     * Delete a task
     *
     * @param identity of task
     * @throws ResultException when error
     */
    void delete(String identity) throws ResultException;

    /**
     * Commit task percentage
     *
     * @param identity   of task
     * @param percentage number
     * @throws ResultException when error
     */
    void commitPercentage(String identity, int percentage) throws ResultException;

    /**
     * Commit error of task when failed
     *
     * @param identity of task
     * @param error    of String
     * @throws ResultException when error
     */
    void commitError(String identity, String error) throws ResultException;

    /**
     * Commit result of task when finished
     *
     * @param identity of task
     * @param result   obj object
     * @throws ResultException when error
     */
    void commitResult(String identity, Object result) throws ResultException;
}
