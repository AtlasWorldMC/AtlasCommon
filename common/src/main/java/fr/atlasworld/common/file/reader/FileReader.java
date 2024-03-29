package fr.atlasworld.common.file.reader;

import com.google.common.base.Preconditions;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * File Reader, allows the reading/writing and even checking of file integrity.
 *
 * @param <V> type of data expected to be loaded.
 */
public abstract class FileReader<V> {
    @SuppressWarnings("deprecation")
    private static final HashFunction CHECKSUM_ALGORITHM = Hashing.sha1();

    protected final File file;

    protected FileReader(@NotNull File file) {
        Preconditions.checkNotNull(file);
        this.file = file;
    }

    /**
     * Read the file.
     *
     * @return file data.
     * @throws IOException if the file could not be read
     */
    public abstract V read() throws IOException;

    /**
     * Write to the file.
     *
     * @param value data to write.
     * @throws IOException if the data could not be written to the file.
     */
    public abstract void write(V value) throws IOException;

    /**
     * Read the file or create the file
     *
     * @param defValue the default value if the file does not exist.
     * @return the read or default value of the file.
     * @throws IOException if the file could not be created or read.
     */
    @Nullable
    public final V readElseCreate(V defValue) throws IOException {
        if (!this.exists()) {
            this.create();
            this.write(defValue);
        }

        return this.read();
    }

    /**
     * Checks if the file exists.
     *
     * @return true if the file exists.
     */
    public boolean exists() {
        return this.file.exists() && this.file.isFile();
    }

    /**
     * Create the file.
     *
     * @return true if the operation was successful.
     * @throws IOException if the file could not be created.$
     */
    public boolean create() throws IOException {
        if (this.exists()) {
            throw new FileAlreadyExistsException(this.file.getAbsolutePath());
        }

        if (!this.file.getParentFile().isDirectory()) {
            this.file.getParentFile().mkdirs();
        }

        return this.file.createNewFile();
    }

    /**
     * Calculate the checksum of the file.
     * This will use {@code SHA-1} algorithm to calculate the checksum.
     *
     * @return the calculated checksum of the file.
     * @throws IOException if the file could not be read.
     */
    public String checksum() throws IOException {
        return this.checksum(CHECKSUM_ALGORITHM);
    }

    /**
     * Calculate the checksum of the file using the provided hashing function.
     *
     * @param func hashing function, or you can use {@link #checksum()} to use the default algorithm.
     * @return the calculated checksum of the file.
     * @throws IOException if the file could not be read.
     */
    public String checksum(HashFunction func) throws IOException {
        ByteSource fileBytes = Files.asByteSource(this.file);
        return fileBytes.hash(func).toString();
    }

    /**
     * Retrieve the file this reader is handling.
     *
     * @return file this reader is handling.
     */
    public final File getFile() {
        return this.file;
    }
}
