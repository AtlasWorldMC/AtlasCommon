package fr.atlasworld.common.exception.crypto;

import fr.atlasworld.common.crypto.Encryptor;

import java.security.GeneralSecurityException;

/**
 * Cryptographic exception, usually thrown by {@link Encryptor}s
 */
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
