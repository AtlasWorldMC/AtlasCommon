package fr.atlasworld.common.concurrent.action;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class FutureActionAdapter<V, C extends FutureActionAdapter<V, C>> implements FutureAction<V> {
    protected final CountDownLatch latch = new CountDownLatch(1);

    protected final List<Consumer<V>> onSuccess;
    protected final List<Consumer<Throwable>> onFailure;
    protected final List<BiConsumer<V, Throwable>> whenDone;
    protected final Stopwatch stopwatch;

    protected boolean done = false;
    protected boolean success = false;

    protected V value;
    protected Throwable cause;

    protected FutureActionAdapter() {
        this.onSuccess = new ArrayList<>();
        this.onFailure = new ArrayList<>();
        this.whenDone = new ArrayList<>();
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    @CanIgnoreReturnValue
    public C onSuccess(@NotNull Consumer<V> listener) {
        if (this.success())
            listener.accept(this.value);

        if (this.isDone())
            return self();

        this.onSuccess.add(listener);
        return self();
    }

    @Override
    @CanIgnoreReturnValue
    public C onFailure(@NotNull Consumer<Throwable> listener) {
        if (!this.success() && this.isDone())
            listener.accept(this.cause);

        if (this.isDone())
            return self();

        this.onFailure.add(listener);
        return self();
    }

    @Override
    @CanIgnoreReturnValue
    public C whenDone(BiConsumer<V, Throwable> listener) {
        if (this.isDone()) {
            listener.accept(this.value, this.cause);
            return self();
        }

        this.whenDone.add(listener);
        return self();
    }

    @Override
    public boolean success() {
        return this.done && this.success;
    }

    @Override
    public Throwable cause() {
        return this.cause;
    }

    @Override
    public @Nullable V result() {
        return this.value;
    }

    @Override
    public V sync() throws InterruptedException {
        this.latch.await();
        return this.value;
    }

    @Override
    public @Nullable V sync(long time, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        if (!this.latch.await(time, unit))
            throw new TimeoutException("Future Action timed-out");

        return this.value;
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
        return this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isDone() {
        return this.done;
    }

    @Override
    public V get() throws InterruptedException {
        return this.sync();
    }

    @Override
    public V get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, TimeoutException {
        return this.sync(timeout, unit);
    }

    @SuppressWarnings("unchecked")
    private C self() {
        return (C) this;
    }
}
