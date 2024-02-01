package fr.atlasworld.common.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

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

    public static SecretKey generateAESSecret() {
        return AES_KEY_GENERATOR.generateKey();
    }

    public static KeyPair generateRSAKeyPair() {
        return RSA_KEY_PAIR_GENERATOR.generateKeyPair();
    }
}
