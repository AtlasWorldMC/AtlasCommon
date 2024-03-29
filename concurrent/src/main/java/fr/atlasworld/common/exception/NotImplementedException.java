package fr.atlasworld.common.exception;

/**
 * Runtime utility exception, thrown when a called method is not yet implemented.
 */
public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("This feature is not yet implemented!");
    }
}
