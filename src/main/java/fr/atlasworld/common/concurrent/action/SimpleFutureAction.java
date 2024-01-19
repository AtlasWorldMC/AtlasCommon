package fr.atlasworld.common.concurrent.action;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Quite similar to {@link CompletableFuture} it allows to make a future that will be completed.
 * And allows other threads to block until it fails or succeeds.
 * @param <V> type of value that the actions is supposed to return.
 */
public class SimpleFutureAction<V> extends FutureActionAdapter<V, SimpleFutureAction<V>> {

    private final ExecutorService executor;

    private Function<Boolean, Boolean> onCancellation = interrupt -> false;
    private boolean cancelled = false;
    private boolean hasTimeout = false;

    public SimpleFutureAction() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Define the listener when {@link #cancel(boolean)} is called.
     * @param listener function called when the future is cancelled.
     *                 It provides a {@code BOOLEAN} whether it should be interrupted or not.
     *                 And expects another {@code BOOLEAN} as result on whether the future has been cancelled.
     */
    @CanIgnoreReturnValue
    public SimpleFutureAction<V> onCancellation(Function<Boolean, Boolean> listener) {
        this.onCancellation = listener;
        return this;
    }

    public SimpleFutureAction<V> timeout(long time, TimeUnit unit) {
        if (this.hasTimeout)
            throw new IllegalArgumentException("Timeout on this action has already been defined!");

        this.hasTimeout = true;
        this.executor.submit(() -> {
            try {
                this.sync(time, unit);
            } catch (TimeoutException e) {
                this.cancel(true);
                this.fail(e);
            } catch (InterruptedException ignored) {}
        });

        return this;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (this.isDone())
            return false; // Action is already done, cannot be canceled.

        this.cancelled = this.onCancellation.apply(mayInterruptIfRunning);

        if (this.cancelled)
            this.timing.stop();

        return this.cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Complete the future action.
     * @param value to complete the future.
     */
    public SimpleFutureAction<V> complete(V value) {
        if (this.isDone())
            throw new IllegalStateException("Action already completed!");

        this.timing.stop();
        this.value = value;
        this.done = true;
        this.success = true;
        this.latch.countDown();

        for (Consumer<V> listener : this.onSuccess) {
            listener.accept(this.value);
        }

        for (BiConsumer<V, Throwable> listener : this.whenDone) {
            listener.accept(this.value, this.cause);
        }

        return this;
    }

    /**
     * Marks the future action as failed.
     * @param cause cause of the failure.
     */
    public SimpleFutureAction<V> fail(Throwable cause) {
        if (this.isDone())
            throw new IllegalStateException("Action already completed!");

        this.timing.stop();
        this.cause = cause;
        this.done = true;
        this.latch.countDown();

        for (Consumer<Throwable> listener : this.onFailure) {
            listener.accept(this.cause);
        }

        for (BiConsumer<V, Throwable> listener : this.whenDone) {
            listener.accept(this.value, this.cause);
        }

        return this;
    }
}
