package fr.atlasworld.common.file.reader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JsonFileReader<T> extends FileReader<T> {
    private final StringFileReader reader;
    private final Gson gson;
    private final Type type;

    public JsonFileReader(@NotNull File file, @NotNull Charset charset, @NotNull Gson gson, @NotNull Type type) {
        super(file);

        this.reader = new StringFileReader(this.file, charset);
        this.gson = gson;
        this.type = type;
    }

    public JsonFileReader(@NotNull File file, Type type) {
        this(file, StandardCharsets.UTF_8, new Gson(), type);
    }

    @Override
    public T read() throws IOException {
        return this.gson.fromJson(this.readRaw(), this.type);
    }

    public JsonElement readRaw() throws IOException {
        return JsonParser.parseString(this.reader.read());
    }

    @Override
    public void write(T value) throws IOException {
        this.writeRaw(this.gson.toJsonTree(value));
    }

    public void writeRaw(JsonElement json) throws IOException {
        String str = this.gson.toJson(json);
        this.reader.write(str);
    }
}
