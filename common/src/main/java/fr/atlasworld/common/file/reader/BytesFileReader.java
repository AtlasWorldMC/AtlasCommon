package fr.atlasworld.common.file.reader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Bytes reader, reads bytes from a file.
 */
public class BytesFileReader extends FileReader<byte[]> {

    public BytesFileReader(@NotNull File file) {
        super(file);
    }

    @Override
    public byte[] read() throws IOException {
        return Files.readAllBytes(this.file.toPath());
    }

    @Override
    public void write(byte[] value) throws IOException {
        Files.write(this.file.toPath(), value);
    }

    /**
     * Get the file as an input stream
     *
     * @return file input stream
     * @throws IOException if the file could not be read.
     */
    public InputStream asStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
