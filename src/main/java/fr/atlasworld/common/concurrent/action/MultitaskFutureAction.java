package fr.atlasworld.common.concurrent.action;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import fr.atlasworld.common.concurrent.Timing;
import fr.atlasworld.common.exception.GroupedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The Multitask Future Action can wait on multiple tasks to finish before firing the complete listener.
 *
 * @param <V>
 */
public class MultitaskFutureAction<V> implements FutureAction<V> {
    private final CountDownLatch latch;

    private final List<Consumer<V>> onSuccess;
    private final List<Consumer<Throwable>> onFailure;
    private final List<BiConsumer<V, Throwable>> whenDone;
    private final Timing timing;
    private final ExecutorService executor;
    private final List<V> values;
    private final List<Throwable> causes;
    private boolean done = false;
    private boolean hasTimeout;

    public MultitaskFutureAction(int taskCount) {
        Preconditions.checkArgument(taskCount > 0, "There must be at least 1 task.");

        this.latch = new CountDownLatch(taskCount);

        this.onSuccess = new ArrayList<>();
        this.onFailure = new ArrayList<>();
        this.whenDone = new ArrayList<>();

        this.timing = new Timing();
        this.executor = Executors.newSingleThreadExecutor();

        this.values = new ArrayList<>();
        this.causes = new ArrayList<>();
    }

    @Override
    @CanIgnoreReturnValue
    public MultitaskFutureAction<V> onSuccess(@NotNull Consumer<V> listener) {
        this.onSuccess.add(listener);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public MultitaskFutureAction<V> onFailure(@NotNull Consumer<Throwable> listener) {
        this.onFailure.add(listener);
        return this;
    }

    @Override
    public MultitaskFutureAction<V> whenDone(BiConsumer<V, Throwable> listener) {
        this.whenDone.add(listener);
        return this;
    }

    @Override
    public FutureAction<V> timeout(long time, TimeUnit unit) {
        if (this.hasTimeout)
            throw new IllegalArgumentException("Timeout on this action has already been defined!");

        this.hasTimeout = true;
        this.executor.submit(() -> {
            try {
                this.sync(time, unit);
            } catch (TimeoutException e) {
                this.failTask(e);
            } catch (InterruptedException ignored) {}
        });

        return this;
    }

    @Override
    public boolean success() {
        return this.done && this.values.size() > this.causes.size();
    }

    @Override
    public @Nullable GroupedException cause() {
        if (!this.isDone())
            return null;

        return this.causes.isEmpty() ? null : new GroupedException(this.causes);
    }

    @Override
    public @Nullable V result() {
        if (!this.isDone())
            return null;

        return this.values.isEmpty() ? null : this.values.get(0);
    }

    /**
     * Retrieve all results.
     * @return all results.
     */
    public @Nullable List<V> results() {
        if (!this.isDone())
            return null;

        return this.values.isEmpty() ? null : this.values;
    }

    @Override
    public V sync() throws InterruptedException {
        this.latch.await();
        return this.values.isEmpty() ? null : this.values.get(0);
    }

    @Override
    public @Nullable V sync(long time, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!this.latch.await(time, unit))
            throw new TimeoutException("Future Action timed-out");

        return this.values.isEmpty() ? null : this.values.get(0);
    }

    @Override
    public V syncUninterruptibly() {
        try {
            return this.sync();
        } catch (InterruptedException e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    public long getRunningTime() {
        return this.timing.getRunningTime();
    }

    /**
     * @deprecated Multitask future actions cannot be cancelled.
     */
    @Override
    @Deprecated
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false; // Multitask Future Action cannot be canceled. Individual tasks should be canceled instead.
    }

    /**
     * @deprecated Multitask future actions cannot be cancelled.
     */
    @Override
    @Deprecated
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.done;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return this.sync();
    }

    @Override
    public V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        return this.sync(timeout, unit);
    }

    public void completeTask(V value) {
        if (this.done)
            throw new IllegalStateException("All tasks are already finished.");

        this.values.add(value);
        this.latch.countDown();

        for (Consumer<V> listener : this.onSuccess) {
            listener.accept(value);
        }

        for (BiConsumer<V, Throwable> listener : this.whenDone) {
            listener.accept(value, null);
        }

        this.done = this.latch.getCount() < 1;
    }

    public void failTask(Throwable cause) {
        if (this.done)
            throw new IllegalStateException("All tasks are already finished.");

        this.causes.add(cause);
        this.latch.countDown();

        for (Consumer<Throwable> listener : this.onFailure) {
            listener.accept(cause);
        }

        for (BiConsumer<V, Throwable> listener : this.whenDone) {
            listener.accept(null, cause);
        }

        this.done = this.latch.getCount() < 1;
    }

    public List<V> getValues() {
        if (!this.done)
            return null;

        return values;
    }

    public List<Throwable> getCauses() {
        if (!this.done)
            return null;

        return causes;
    }
}
