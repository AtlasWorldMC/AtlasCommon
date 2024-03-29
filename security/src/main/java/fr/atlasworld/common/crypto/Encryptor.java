package fr.atlasworld.common.crypto;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public interface Encryptor {

    /**
     * Encrypt bytes with the provided key.
     *
     * @param bytes bytes to encrypt.
     * @return encrypted bytes.
     * @throws IllegalStateException if this cipher is in a wrong state
     * (e.g., has not been initialized)
     * @throws IllegalBlockSizeException if this cipher is a block cipher,
     * no padding has been requested (only in encryption mode), and the total
     * input length of the data processed by this cipher is not a multiple of
     * block size; or if this encryption algorithm is unable to
     * process the input data provided.
     * @throws BadPaddingException if this cipher is in decryption mode,
     * and (un)padding has been requested, but the decrypted data is not
     * bounded by the appropriate padding bytes
     * @throws AEADBadTagException if this cipher is decrypting in an
     * AEAD mode (such as GCM/CCM), and the received authentication tag
     * does not match the calculated value
     */
    byte[] encrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Decrypts bytes with the provided key.
     *
     * @param bytes bytes to decrypt.
     * @return decrypted bytes.
     * @throws IllegalStateException if this cipher is in a wrong state
     * (e.g., has not been initialized)
     * @throws IllegalBlockSizeException if this cipher is a block cipher,
     * no padding has been requested (only in encryption mode), and the total
     * input length of the data processed by this cipher is not a multiple of
     * block size; or if this encryption algorithm is unable to
     * process the input data provided.
     * @throws BadPaddingException if this cipher is in decryption mode,
     * and (un)padding has been requested, but the decrypted data is not
     * bounded by the appropriate padding bytes
     * @throws AEADBadTagException if this cipher is decrypting in an
     * AEAD mode (such as GCM/CCM), and the received authentication tag
     * does not match the calculated value
     */
    byte[] decrypt(byte[] bytes) throws IllegalBlockSizeException, BadPaddingException;

    /**
     * Checks if the encryptor key is a public key.
     *
     * @return true if the encryptor has a public key, false otherwise.
     */
    boolean isPublic();

    /**
     * Checks if the key a private key.
     *
     * @return true if the encryptor has a private key, false otherwise.
     */
    boolean isPrivate();

    /**
     * Checks if the key is a secret key.
     *
     * @return true if the encryptor has a secret key, false otherwise.
     */
    boolean isSecret();
}
