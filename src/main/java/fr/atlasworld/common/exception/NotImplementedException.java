package fr.atlasworld.common.exception;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super("This feature is not yet implemented!");
    }
}
