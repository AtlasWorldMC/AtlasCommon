package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Secret-Key encryptor, Encryptor for handling symmetric key for encryption/decryption.
 */
public class SecretKeyEncryptor implements Encryptor {

    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    /**
     * Create a new KeyPair encryptor
     *
     * @param secretKey key for encryption.
     *
     * @throws IllegalArgumentException if the both keys are null.
     * @throws NoSuchAlgorithmException if {@code transformation}
     *         is {@code null}, empty, in an invalid format,
     *         or if no {@code Provider} supports a {@code CipherSpi}
     *         implementation for the specified algorithm.
     * @throws NoSuchPaddingException if {@code transformation}
     *         contains a padding scheme that is not available.
     * @exception InvalidKeyException if the given key is inappropriate for
     *         initializing this cipher, or requires
     *         algorithm parameters that cannot be
     *         determined from the given key, or if the given key has a keysize that
     *         exceeds the maximum allowable keysize (as determined from the
     *         configured jurisdiction policy files).
     */
    public SecretKeyEncryptor(@NotNull SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkNotNull(secretKey);

        this.encryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

        this.decryptCipher = Cipher.getInstance(secretKey.getAlgorithm());
        this.decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
    }

    @Override
    public byte[] encrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        return this.encryptCipher.doFinal(bytes);
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        return this.decryptCipher.doFinal(bytes);
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
