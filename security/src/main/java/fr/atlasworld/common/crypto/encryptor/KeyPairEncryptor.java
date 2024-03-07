package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyPairEncryptor implements Encryptor {
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public KeyPairEncryptor(PublicKey publicKey, PrivateKey privateKey) throws CryptographyException {
        Preconditions.checkArgument(!(publicKey == null && privateKey == null), "At least one key must not be null!");

        try {
            if (publicKey != null) {
                this.encryptCipher = Cipher.getInstance(publicKey.getAlgorithm());
                this.encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            }

            if (privateKey != null) {
                this.decryptCipher = Cipher.getInstance(privateKey.getAlgorithm());
                this.decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            }
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not initialize ciphers.", e);
        }
    }

    public KeyPairEncryptor(KeyPair keys) throws CryptographyException {
        this(keys.getPublic(), keys.getPrivate());
    }

    @Override
    public byte[] encrypt(byte[] bytes) throws CryptographyException {
        if (this.encryptCipher == null)
            throw new IllegalStateException("No Public Key provided!");

        try {
            return this.encryptCipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not encrypt bytes.", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws CryptographyException {
        if (this.decryptCipher == null)
            throw new IllegalStateException("No Private Key provided!");

        try {
            return this.decryptCipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not decrypt bytes.", e);
        }
    }

    @Override
    public boolean isPublic() {
        return this.encryptCipher != null;
    }

    @Override
    public boolean isPrivate() {
        return this.decryptCipher != null;
    }

    @Override
    public boolean isSecret() {
        return false;
    }
}
