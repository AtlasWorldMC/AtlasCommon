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
    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public KeyPairEncryptor(@NotNull PublicKey publicKey, @NotNull PrivateKey privateKey) throws CryptographyException {
        Preconditions.checkNotNull(publicKey, privateKey);

        this.publicKey = publicKey;
        this.privateKey = privateKey;

        try {
            this.encryptCipher = Cipher.getInstance(this.publicKey.getAlgorithm());
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, this.publicKey);

            this.decryptCipher = Cipher.getInstance(this.privateKey.getAlgorithm());
            this.decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not initialize ciphers.", e);
        }
    }

    public KeyPairEncryptor(KeyPair keys) throws CryptographyException {
        this(keys.getPublic(), keys.getPrivate());
    }

    @Override
    public byte[] encrypt(byte[] bytes) throws CryptographyException {
        try {
            return this.encryptCipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not encrypt bytes.", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws CryptographyException {
        try {
            return this.decryptCipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not decrypt bytes.", e);
        }
    }

    @Override
    public boolean isPublic() {
        return true;
    }

    @Override
    public boolean isPrivate() {
        return true;
    }

    @Override
    public boolean isSecret() {
        return false;
    }
}
