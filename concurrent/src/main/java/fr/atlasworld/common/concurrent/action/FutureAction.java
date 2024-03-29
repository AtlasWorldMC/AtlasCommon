package fr.atlasworld.common.concurrent.action;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Future Action, Task finished asynchronously.
 *
 * @param <V> resulting data.
 */
public interface FutureAction<V> extends Future<V> {

    /**
     * Execute code on success.
     *
     * @param listener listener to call when the future is successful.
     */
    @CanIgnoreReturnValue
    FutureAction<V> onSuccess(@NotNull Consumer<V> listener);

    /**
     * Execute code on failure.
     *
     * @param listener listener to call when the future has failed.
     */
    @CanIgnoreReturnValue
    FutureAction<V> onFailure(@NotNull Consumer<Throwable> listener);

    /**
     * Execute code when the action is done, whether is succeeded or failed.
     *
     * @param listener listener to call when the future is done.
     */
    @CanIgnoreReturnValue
    FutureAction<V> whenDone(BiConsumer<V, Throwable> listener);

    /**
     * Add a timeout to the execution.
     * The timeout begins to count down only after this method is called,
     * previous execution time is not counted.
     * if it takes longer than the defined timeout the future will cancel itself and fail
     * with a {@link TimeoutException}
     *
     * @param time time in milliseconds.
     * @param unit time unit.
     */
    @CanIgnoreReturnValue
    FutureAction<V> timeout(long time, TimeUnit unit);

    /**
     * Check if action was successful.
     *
     * @return true if the action is done and was successful.
     */
    boolean success();

    /**
     * Get the cause of the failure.
     *
     * @return null if the action is not yet finished or was successful.
     */
    @Nullable Throwable cause();

    /**
     * Get the result of the future action.
     *
     * @return null if the action is not yet finished or wasn't successful.
     */
    @Nullable V result();

    /**
     * Block till the action is finished. And get the value.
     * <p>
     * Don't use this method extensively, this could slow down the system.
     *
     * @return null on failure.
     * @throws InterruptedException when interrupted
     */
    V sync() throws InterruptedException;

    /**
     * Block till the action is finished. And get the value.
     * <p>
     * Don't use this method extensively, this could slow down the system.
     *
     * @return null on failure.
     * @throws InterruptedException when interrupted
     */
    V sync(long time, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException;

    /**
     * Block till the action is finished.
     * <p>
     * Don't use this method extensively, this could slow down the system.
     *
     * @return null on failure.
     * @throws com.google.common.util.concurrent.UncheckedExecutionException when interrupted
     */
    @CanIgnoreReturnValue
    V syncUninterruptibly();

    /**
     * Returns the running time of the FutureAction.
     *
     * @return the running time in milliseconds.
     */
    long getRunningTime();

    /**
     * Returns the running time of the FutureAction converted to the specified TimeUnit.
     *
     * @param unit the TimeUnit to convert the running time to.
     * @return the running time converted to the specified TimeUnit.
     */
    default long getRunningTime(TimeUnit unit) {
        return unit.convert(this.getRunningTime(), TimeUnit.MILLISECONDS);
    }
}
