package plus.extvos.builtin.async.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import plus.extvos.builtin.async.service.AsyncRunnable;

import java.io.Serializable;

/**
 * @author Mingcai SHEN
 */
public class AsyncTask implements Serializable {

    /**
     * identity of a task, normally will be a UUID
     */
    private String identity;

    /**
     * subject of title of a task for human readable
     */
    private String subject;

    /**
     * status of task: -1: failed, 0: created; 1: running; 2: finished
     */
    private int status;

    /**
     * percentage of a task, from 0 ~ 100
     */
    private int percentage;

    /**
     * error message when task failed
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    /**
     * result of task when finished.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    /**
     * Runnable of the task
     */
    @JsonIgnore
    private AsyncRunnable runnable;

    public AsyncTask() {
        identity = null;
        subject = null;
        status = 0;
        error = null;
        result = null;
        percentage = 0;
        runnable = null;
    }

    public AsyncTask(String identity, AsyncRunnable runnable, String subject) {
        this();
        this.identity = identity;
        this.runnable = runnable;
        this.subject = subject;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public AsyncRunnable getRunnable() {
        return runnable;
    }
}
