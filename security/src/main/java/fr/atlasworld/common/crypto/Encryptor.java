package fr.atlasworld.common.crypto;

import fr.atlasworld.common.exception.crypto.CryptographyException;

public interface Encryptor {

    /**
     * Encrypt bytes with the provided key.
     *
     * @param bytes bytes to encrypt.
     * @return encrypted bytes.
     * @throws CryptographyException if the bytes could not be encrypted.
     */
    byte[] encrypt(byte[] bytes) throws CryptographyException;

    /**
     * Decrypts bytes with the provided key.
     *
     * @param bytes bytes to decrypt.
     * @return decrypted bytes.
     * @throws CryptographyException if the bytes could not be decrypted.
     */
    byte[] decrypt(byte[] bytes) throws CryptographyException;

    /**
     * Checks if the key is a public key.
     */
    boolean isPublic();

    /**
     * Checks if the key a private key.
     */
    boolean isPrivate();

    /**
     * Checks if the key is a secret key.
     */
    boolean isSecret();
}
