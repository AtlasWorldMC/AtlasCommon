package fr.atlasworld.common.exception;

import java.util.Arrays;
import java.util.List;

/**
 * Contains a group of exceptions
 */
public class GroupedException extends Exception {
    private final List<Throwable> causes;

    public GroupedException(Throwable... causes) {
        this(Arrays.asList(causes));
    }

    public GroupedException(List<Throwable> causes) {
        this.causes = causes;
    }

    public GroupedException(String message, Throwable... causes) {
        this(message, Arrays.asList(causes));
    }

    public GroupedException(String message, List<Throwable> causes) {
        super(message);
        this.causes = causes;
    }

    public synchronized List<Throwable> getCauses() {
        return causes;
    }

    @Override
    public synchronized Throwable getCause() {
        return null;
    }
}
