package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

/**
 * Key-Pair encryptor, Encryptor for handling Asymmetric Keys for encryption/decryption.
 */
public class KeyPairEncryptor implements Encryptor {
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    /**
     * Create a new KeyPair encryptor
     *
     * @param publicKey nullable, public key.
     * @param privateKey nullable, private key.
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
    public KeyPairEncryptor(@Nullable PublicKey publicKey, @Nullable PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Preconditions.checkArgument(!(publicKey == null && privateKey == null), "At least one key must not be null!");

        if (publicKey != null) {
            this.encryptCipher = Cipher.getInstance(publicKey.getAlgorithm());
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        }

        if (privateKey != null) {
            this.decryptCipher = Cipher.getInstance(privateKey.getAlgorithm());
            this.decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        }
    }

    /**
     * Create a new KeyPair encryptor
     *
     * @param keys keypair for encryption.
     *
     * @throws NullPointerException if the keypair is null.
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
    public KeyPairEncryptor(@NotNull KeyPair keys) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this(keys.getPublic(), keys.getPrivate());
    }

    @Override
    public byte[] encrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        if (this.encryptCipher == null)
            throw new IllegalStateException("No Public Key provided!");

        return this.encryptCipher.doFinal(bytes);
    }

    @Override
    public byte[] decrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        if (this.decryptCipher == null)
            throw new IllegalStateException("No Private Key provided!");

        return this.decryptCipher.doFinal(bytes);
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
