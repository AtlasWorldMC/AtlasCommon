package fr.atlasworld.common.concurrent.action;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Composite Future Action, Basically it combines multiple future actions into one.
 * This allows for code to be executed once a group of futures is completed.
 * <p>
 * A downside is that you will not be able to retrieve data from those futures here.
 * If you plan to expect data from them, you will be required to hold an instance of them.
 */
public final class CompositeFutureAction implements FutureAction<Void> {
    private final List<FutureAction<?>> composedFutures;
    private final Stopwatch stopwatch;

    private final CountDownLatch latch = new CountDownLatch(1);
    private final ExecutorService executor;

    private final List<Consumer<Void>> onSuccess;
    private final List<Consumer<Throwable>> onFailure;
    private final List<BiConsumer<Void, Throwable>> whenDone;

    private boolean hasTimeout = false;
    private boolean cancelled = false;
    private int futureDoneCount = 0;

    private Throwable cause;

    private CompositeFutureAction(List<FutureAction<?>> composedFutures) {
        this.composedFutures = composedFutures;
        this.stopwatch = Stopwatch.createStarted();

        this.executor = Executors.newSingleThreadExecutor();

        this.onSuccess = new ArrayList<>();
        this.onFailure = new ArrayList<>();
        this.whenDone = new ArrayList<>();
    }

    public CompositeFutureAction() {
        this(new ArrayList<>());
    }

    public CompositeFutureAction(FutureAction<?>... actions) {
        this(new ArrayList<>());

        this.composedFutures.addAll(List.of(actions));
    }

    private void initialize() {
        this.composedFutures.forEach(future ->
                future.whenDone((unused, cause) -> {
                    this.futureDoneCount++;

                    if (cause != null && this.cause != null)
                        this.cause = new Exception("One or more of the futures failed execution.", cause);

                    if (this.isDone()) {
                        if (this.success())
                            this.onSuccess.forEach(listener -> listener.accept(null));
                        else
                            this.onFailure.forEach(listener -> listener.accept(this.cause));

                        this.whenDone.forEach(listener -> listener.accept(null, this.cause));
                    }
                }));

        this.whenDone(((unused, throwable) -> this.stopwatch.stop()));
    }

    @Override
    public CompositeFutureAction onSuccess(@NotNull Consumer<Void> listener) {
        if (this.success())
            listener.accept(null);

        if (this.isDone())
            return this;

        this.onSuccess.add(listener);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public CompositeFutureAction onFailure(@NotNull Consumer<Throwable> listener) {
        if (!this.success() && this.isDone())
            listener.accept(this.cause);

        if (this.isDone())
            return this;

        this.onFailure.add(listener);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public CompositeFutureAction whenDone(BiConsumer<Void, Throwable> listener) {
        if (this.isDone()) {
            listener.accept(null, this.cause);
            return this;
        }

        this.whenDone.add(listener);
        return this;
    }


    public CompositeFutureAction timeout(long time, TimeUnit unit) {
        if (this.hasTimeout)
            throw new IllegalArgumentException("Timeout on this action has already been defined!");

        this.hasTimeout = true;
        this.executor.submit(() -> {
            try {
                this.sync(time, unit);
            } catch (TimeoutException e) {
                this.cancel(true);
                this.cause = e;
            } catch (InterruptedException ignored) {
            } finally {
                this.executor.shutdown(); // Free the thread's resources.
            }
        });

        return this;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        for (FutureAction<?> future : this.composedFutures) {
            if (future.isCancelled())
                continue;

            if (!future.cancel(mayInterruptIfRunning))
                this.cancelled = false;
        }

        return this.cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.futureDoneCount >= this.composedFutures.size();
    }

    @Override
    public Void get() throws InterruptedException {
        return this.sync();
    }

    @Override
    public Void get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        return this.sync(timeout, unit);
    }

    /**
     * Returns false if at least on the futures fails.
     *
     * @return true if all futures could be completed without any failure, false otherwise.
     */
    @Override
    public boolean success() {
        return this.isDone() && this.cause != null && !this.cancelled;
    }

    @Override
    public Throwable cause() {
        return this.cause;
    }

    @Override
    @Deprecated
    public @Nullable Void result() {
        return null;
    }

    @Override
    public Void sync() throws InterruptedException {
        this.latch.await();

        return null;
    }

    @Override
    public @Nullable Void sync(long time, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!this.latch.await(time, unit))
            throw new TimeoutException("Future Action timed-out");

        return null;
    }

    @Override
    public Void syncUninterruptibly() {
        try {
            return this.sync();
        } catch (InterruptedException e) {
            throw new UncheckedExecutionException(e);
        }
    }

    @Override
    public long getRunningTime() {
        return this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }
}
