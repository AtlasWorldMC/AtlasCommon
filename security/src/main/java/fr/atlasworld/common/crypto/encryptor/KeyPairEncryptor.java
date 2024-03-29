package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class KeyPairEncryptor implements Encryptor {
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    public KeyPairEncryptor(PublicKey publicKey, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
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

    public KeyPairEncryptor(KeyPair keys) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
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
