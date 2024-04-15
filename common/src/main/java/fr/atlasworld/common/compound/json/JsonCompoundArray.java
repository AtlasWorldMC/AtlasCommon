package fr.atlasworld.common.compound.json;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fr.atlasworld.common.compound.CompoundArray;
import fr.atlasworld.common.compound.CompoundElement;
import fr.atlasworld.common.compound.CompoundObject;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class JsonCompoundArray extends JsonCompoundElement implements CompoundArray {
    private final JsonArray array;

    public JsonCompoundArray(JsonArray array) {
        super(array);
        this.array = array;
    }

    public JsonCompoundArray() {
        this(new JsonArray());
    }


    @Override
    public CompoundArray addObject(@NotNull Consumer<CompoundObject> builder) {
        Preconditions.checkNotNull(builder);

        JsonCompoundObject jsonObject = new JsonCompoundObject();
        builder.accept(jsonObject);

        this.array.add(jsonObject.getObject());
        return this;
    }

    @Override
    public CompoundArray addArray(@NotNull Consumer<CompoundArray> builder) {
        Preconditions.checkNotNull(builder);

        JsonCompoundArray jsonArray = new JsonCompoundArray();
        builder.accept(jsonArray);

        this.array.add(jsonArray.getArray());
        return this;
    }

    @Override
    public CompoundArray add(@NotNull CompoundElement value) {
        Preconditions.checkNotNull(value);
        Preconditions.checkArgument(value instanceof JsonCompoundElement, "Only Json Backed compound are supported.");

        this.array.add(fromCompound((JsonCompoundElement) value));
        return this;
    }

    @Override
    public CompoundArray add(boolean value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(double value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(long value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(int value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(byte value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(byte[] value) {
        Preconditions.checkNotNull(value);

        this.array.add(Base64.getEncoder().encodeToString(value));

        return this;
    }

    @Override
    public CompoundArray add(char value) {
        this.array.add(value);
        return this;
    }

    @Override
    public CompoundArray add(@NotNull String value) {
        Preconditions.checkNotNull(value);

        this.array.add(value);

        return this;
    }

    @Override
    public CompoundArray addAll(@NotNull CompoundArray array) {
        Preconditions.checkNotNull(array);
        Preconditions.checkArgument(array instanceof JsonCompoundArray, "Only Json Backed compound are supported.");

        this.array.addAll(((JsonCompoundArray) array).getArray());

        return this;
    }

    @Override
    public CompoundElement setObject(int index, @NotNull Consumer<CompoundObject> builder) {
        Preconditions.checkNotNull(builder);

        JsonCompoundObject jsonObject = new JsonCompoundObject();
        builder.accept(jsonObject);

        JsonElement oldElement = this.array.set(index, jsonObject.getObject());
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement setArray(int index, @NotNull Consumer<CompoundArray> builder) {
        Preconditions.checkNotNull(builder);

        JsonCompoundArray jsonArray = new JsonCompoundArray();
        builder.accept(jsonArray);

        JsonElement oldElement = this.array.set(index, jsonArray.getArray());
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, @NotNull CompoundElement element) {
        Preconditions.checkNotNull(element);
        Preconditions.checkArgument(element instanceof JsonCompoundElement, "Only Json Backed compound are supported.");

        JsonElement oldElement = this.array.set(index, fromCompound((JsonCompoundElement) element));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, boolean value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, double value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, long value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, int value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, byte value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, byte[] value) {
        Preconditions.checkNotNull(value);

        JsonElement oldElement = this.array.set(index, new JsonPrimitive(Base64.getEncoder().encodeToString(value)));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, char value) {
        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public CompoundElement set(int index, @NotNull String value) {
        Preconditions.checkNotNull(value);

        JsonElement oldElement = this.array.set(index, new JsonPrimitive(value));
        return toCompound(oldElement);
    }

    @Override
    public boolean remove(@NotNull CompoundElement element) {
        Preconditions.checkNotNull(element);
        Preconditions.checkArgument(element instanceof JsonCompoundElement, "Only Json Backed compound are supported.");

        return this.array.remove(fromCompound((JsonCompoundElement) element));
    }

    @Override
    public CompoundElement remove(int index) {
        return toCompound(this.array.remove(index));
    }

    @Override
    public boolean contains(@NotNull CompoundElement element) {
        Preconditions.checkNotNull(element);
        Preconditions.checkArgument(element instanceof JsonCompoundElement, "Only Json Backed compound are supported.");

        return this.array.contains(fromCompound((JsonCompoundElement) element));
    }

    @Override
    public int size() {
        return this.array.size();
    }

    @Override
    public boolean isEmpty() {
        return this.array.isEmpty();
    }

    @Override
    public CompoundElement get(int index) {
        return toCompound(this.array.get(index));
    }

    @Override
    public List<CompoundElement> asList() {
        return this.array.asList()
                .stream()
                .map(element -> (CompoundElement) toCompound(element))
                .toList();
    }

    @NotNull
    @Override
    public Iterator<CompoundElement> iterator() {
        return this.asList().iterator();
    }

    @Override
    public JsonCompoundArray clone() {
        return (JsonCompoundArray) super.clone();
    }

    public JsonArray getArray() {
        return array;
    }
}
