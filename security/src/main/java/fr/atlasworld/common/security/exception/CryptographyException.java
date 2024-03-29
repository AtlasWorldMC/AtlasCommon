package fr.atlasworld.common.security.exception;

import fr.atlasworld.common.security.Encryptor;
import org.jetbrains.annotations.ApiStatus;

import java.security.GeneralSecurityException;

/**
 * Cryptographic exception, usually thrown by {@link Encryptor}s
 */
@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion = "1.1.0")
public class CryptographyException extends GeneralSecurityException {

    public CryptographyException() {
    }

    public CryptographyException(String msg) {
        super(msg);
    }

    public CryptographyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptographyException(Throwable cause) {
        super(cause);
    }
}
