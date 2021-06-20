package org.extvos.builtin.async.service;

import org.extvos.builtin.async.dto.AsyncTask;
import org.extvos.restlet.exception.RestletException;

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
     * @throws RestletException when error
     */
    AsyncTask make(AsyncRunnable r, String subject) throws RestletException;

    /**
     * Get AsyncTask by id
     *
     * @param identity of string
     * @return AsyncTask instance
     * @throws RestletException when error
     */
    AsyncTask get(String identity) throws RestletException;

    /**
     * Set AsyncTask
     *
     * @param identity of string
     * @param t        AsyncTask instance
     * @throws RestletException when error
     */
    void set(String identity, AsyncTask t) throws RestletException;

    /**
     * Delete a task
     *
     * @param identity of task
     * @throws RestletException when error
     */
    void delete(String identity) throws RestletException;

    /**
     * Commit task percentage
     *
     * @param identity   of task
     * @param percentage number
     * @throws RestletException when error
     */
    void commitPercentage(String identity, int percentage) throws RestletException;

    /**
     * Commit error of task when failed
     *
     * @param identity of task
     * @param error    of String
     * @throws RestletException when error
     */
    void commitError(String identity, String error) throws RestletException;

    /**
     * Commit result of task when finished
     *
     * @param identity of task
     * @param result   obj object
     * @throws RestletException when error
     */
    void commitResult(String identity, Object result) throws RestletException;
}
