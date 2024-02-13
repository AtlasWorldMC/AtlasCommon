package fr.atlasworld.common.file.reader;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StringFileReader extends FileReader<String> {
    private final Charset charset;

    public StringFileReader(@NotNull File file, @NotNull Charset charset) {
        super(file);
        this.charset = charset;
    }

    public StringFileReader(@NotNull File file) {
        this(file, StandardCharsets.UTF_8);
    }

    @Override
    public String read() throws IOException {
        return Files.readString(this.file.toPath(), this.charset);
    }

    @Override
    public void write(String value) throws IOException {
        Files.writeString(this.file.toPath(), value, this.charset);
    }
}
