package plus.extvos.builtin.async.service;

/**
 * @author Mingcai SHEN
 */
public interface AsyncRunnable {
    /**
     * A runnable interface
     *
     * @param ai The async indicator which allowed to report execution percentage.
     * @return Result of object
     * @throws RuntimeException when error
     */
    Object run(AsyncIndicator ai) throws RuntimeException;
}
