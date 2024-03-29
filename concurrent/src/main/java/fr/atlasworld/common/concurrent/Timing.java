package fr.atlasworld.common.concurrent;

import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.TimeUnit;

/**
 * The Timings class provides a way to measure the running time of a program or a specific section of code.
 *
 * @deprecated Use {@link com.google.common.base.Stopwatch} instead.
 */
@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
public class Timing {
    private long start;
    private long runningTime;

    public Timing() {
        this.restart();
    }

    /**
     * Restarts the timing.
     */
    public void restart() {
        this.start = System.nanoTime();
        this.runningTime = -1L;
    }

    /**
     * Retrieves the start time of the program or a specific section of code.
     *
     * @return The start time in milliseconds.
     */
    public long getStartTime() {
        return this.start;
    }

    /**
     * Retrieves the running time of the program or a specific section of code.
     *
     * @return The running time in milliseconds. If the running time has not been explicitly calculated
     * by calling the {@link Timing#stop()} method, then it calculates the running time by
     * subtracting the start time from the current system time. If the running time has been
     * explicitly calculated, it returns the stored running time.
     */
    public long getRunningTime() {
        return this.runningTime == -1L ?
                (System.nanoTime() - this.start) :
                this.runningTime;
    }

    /**
     * Retrieves the {@link Timing#getRunningTime()} in the specified time unit.
     *
     * @param unit the unit to convert the running time to
     * @return the running time converted to the specified time unit
     */
    public long getRunningTime(TimeUnit unit) {
        return unit.convert(this.getRunningTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * Stops the timer and calculates the running time.
     */
    public void stop() {
        this.runningTime = (System.nanoTime() - this.start);
    }
}
