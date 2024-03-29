package fr.atlasworld.common.crypto.encryptor;

import com.google.common.base.Preconditions;
import fr.atlasworld.common.crypto.Encryptor;
import fr.atlasworld.common.exception.crypto.CryptographyException;
import org.jetbrains.annotations.NotNull;

import javax.crypto.*;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SecretKeyEncryptor implements Encryptor {

    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

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
        return this.encryptCipher.doFinal(bytes);
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
