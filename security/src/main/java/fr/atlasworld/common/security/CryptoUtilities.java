package fr.atlasworld.common.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Utility class for Key Generation.
 */
public class CryptoUtilities {
    private static final KeyGenerator AES_KEY_GENERATOR;
    private static final KeyPairGenerator RSA_KEY_PAIR_GENERATOR;

    static {
        try {
            AES_KEY_GENERATOR = KeyGenerator.getInstance("AES");
            RSA_KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance("RSA");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Unsupported System. AtlasCommon-Security requires that the system supports AES and RSA.", e);
        }
    }

    /**
     * Generate an {@code AES} secret key.
     *
     * @return new {@code AES} secret key.
     */
    public static SecretKey generateAESSecret() {
        return AES_KEY_GENERATOR.generateKey();
    }

    /**
     * Generate a {@code RSA} key pair.
     *
     * @return new {@code RSA} key pair.
     */
    public static KeyPair generateRSAKeyPair() {
        return RSA_KEY_PAIR_GENERATOR.generateKeyPair();
    }
}
