package fr.atlasworld.common.compound.json;

import com.google.common.base.Preconditions;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import fr.atlasworld.common.compound.CompoundArray;
import fr.atlasworld.common.compound.CompoundElement;
import fr.atlasworld.common.compound.CompoundObject;
import fr.atlasworld.common.compound.CompoundPrimitive;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JsonCompoundObject extends JsonCompoundElement implements CompoundObject {
    private final JsonObject object;

    public JsonCompoundObject(JsonObject object) {
        super(object);
        this.object = object;
    }

    public JsonCompoundObject() {
        this(new JsonObject());
    }


    @Override
    public CompoundElement remove(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return new JsonCompoundElement(this.object.remove(key));
    }

    @Override
    public CompoundObject addObject(@NotNull String key, @NotNull Consumer<CompoundObject> builder) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(builder);

        JsonCompoundObject jsonObject = new JsonCompoundObject();
        builder.accept(jsonObject);

        this.object.add(key, jsonObject.object);
        return this;
    }

    @Override
    public CompoundObject addArray(@NotNull String key, @NotNull Consumer<CompoundArray> builder) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(builder);

        JsonCompoundArray jsonArray = new JsonCompoundArray();
        builder.accept(jsonArray);

        this.object.add(key, jsonArray.getArray());
        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, @NotNull CompoundElement value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(value instanceof JsonCompoundElement, "Only Json Backed compound are supported.");

        this.object.add(key, fromCompound((JsonCompoundElement) value));

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, boolean value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, double value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, long value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, int value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, byte value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, byte[] value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        this.object.addProperty(key, Base64.getEncoder().encodeToString(value));

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, char value) {
        Preconditions.checkNotNull(key);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public CompoundObject add(@NotNull String key, @NotNull String value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        this.object.addProperty(key, value);

        return this;
    }

    @Override
    public Set<Map.Entry<String, CompoundElement>> entrySet() {
        return this.object.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), (CompoundElement) toCompound(entry.getValue())))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public int size() {
        return this.object.size();
    }

    @Override
    public boolean isEmpty() {
        return this.object.isEmpty();
    }

    @Override
    public boolean has(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return this.object.has(key);
    }

    @Override
    public CompoundElement get(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return toCompound(this.object.get(key));
    }

    @Override
    public CompoundPrimitive getAsPrimitive(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return this.get(key).getAsPrimitive();
    }

    @Override
    public CompoundArray getAsArray(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return this.get(key).getAsArray();
    }

    @Override
    public CompoundObject getAsObject(@NotNull String key) {
        Preconditions.checkNotNull(key);

        return this.get(key).getAsObject();
    }

    @Override
    public Map<String, CompoundElement> asMap() {
        return this.object.asMap()
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), (CompoundElement) toCompound(entry.getValue())))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public JsonCompoundObject clone() {
        return (JsonCompoundObject) super.clone();
    }

    public JsonObject getObject() {
        return object;
    }
}
