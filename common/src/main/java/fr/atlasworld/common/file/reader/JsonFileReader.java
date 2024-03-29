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

/**
 * Read a file as a JSON format, allows the parsing of the data directly into java objects using {@link Gson}.
 *
 * @param <T> type of data to parse.
 */
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

    /**
     * Read the file.
     *
     * @return file data.
     * @throws IOException if the file could not be read.
     * @throws com.google.gson.JsonParseException if the file could not get parsed into JSON.
     */
    @Override
    public T read() throws IOException {
        return this.gson.fromJson(this.readRaw(), this.type);
    }

    /**
     * Read the raw {@link JsonElement} from the file.
     *
     * @return the raw {@link JsonElement} of the file.
     * @throws IOException if the file could not be written.
     * @throws com.google.gson.JsonParseException if the file could not get parsed into JSON.
     */
    public JsonElement readRaw() throws IOException {
        return JsonParser.parseString(this.reader.read());
    }

    /**
     * Write to the file.
     *
     * @param value data to write.
     * @throws IOException if the data could not be written to the file.
     * @throws com.google.gson.JsonParseException if the data could not get parsed into JSON.
     */
    @Override
    public void write(T value) throws IOException {
        this.writeRaw(this.gson.toJsonTree(value));
    }

    /**
     * Write the raw {@link JsonElement} to the file.
     *
     * @param json json data to write.
     * @throws IOException if the file could not be written.
     */
    public void writeRaw(JsonElement json) throws IOException {
        String str = this.gson.toJson(json);
        this.reader.write(str);
    }
}
