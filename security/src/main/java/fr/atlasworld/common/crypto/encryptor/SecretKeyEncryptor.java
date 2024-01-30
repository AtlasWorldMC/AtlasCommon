package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

public class SecretKeyEncryptor implements Encryptor {

    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public SecretKeyEncryptor(@NotNull SecretKey secretKey) throws CryptographyException {
        Preconditions.checkNotNull(secretKey);

        try {
            this.encryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            this.decryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
            this.decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (GeneralSecurityException e) {
            throw new CryptographyException("Could not initialize ciphers.", e);
        }
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
        return false;
    }

    @Override
    public boolean isPrivate() {
        return false;
    }

    @Override
    public boolean isSecret() {
        return true;
    }
}
