package fr.atlasworld.common.compound.json;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import fr.atlasworld.common.compound.*;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Date;

public class JsonCompoundElement implements CompoundElement {
    private static final Gson GSON = new Gson();

    private final JsonElement element;

    public JsonCompoundElement(@NotNull JsonElement element) {
        Preconditions.checkNotNull(element);

        this.element = element;
    }

    @Override
    public boolean isArray() {
        return this.element.isJsonArray();
    }

    @Override
    public boolean isObject() {
        return this.element.isJsonObject();
    }

    @Override
    public boolean isPrimitive() {
        return this.element.isJsonPrimitive();
    }

    @Override
    public boolean isNull() {
        return this.element.isJsonNull();
    }

    @Override
    public CompoundArray getAsArray() {
        if (this.isArray())
            return new JsonCompoundArray(this.element.getAsJsonArray());

        throw new IllegalStateException("Not a CompoundArray: " + this);
    }

    @Override
    public CompoundObject getAsObject() {
        if (this.isObject())
            return new JsonCompoundObject(this.element.getAsJsonObject());

        throw new IllegalStateException("Not a CompoundObject: " + this);
    }

    @Override
    public CompoundPrimitive getAsPrimitive() {
        if (this.isPrimitive())
            return (CompoundPrimitive) this;

        throw new IllegalStateException("Not a CompoundPrimitive: " + this);
    }

    @Override
    public CompoundNull getAsNull() {
        if (this.isNull())
            return (CompoundNull) this;

        throw new IllegalStateException("Not a CompoundNull: " + this);
    }

    @Override
    public boolean getAsBoolean() {
        return this.element.getAsBoolean();
    }

    @Override
    public double getAsDouble() {
        return this.element.getAsDouble();
    }

    @Override
    public long getAsLong() {
        return this.element.getAsLong();
    }

    @Override
    public int getAsInt() {
        return this.element.getAsInt();
    }

    @Override
    public byte getAsByte() {
        return this.element.getAsByte();
    }

    @Override
    public byte[] getAsByteArray() {
        return Base64.getDecoder().decode(this.element.getAsString());
    }

    @Override
    public Date getAsDate() {
        return new Date(this.element.getAsLong());
    }

    @Override
    public char getAsChar() {
        return this.element.getAsString().charAt(0);
    }

    @Override
    public String getAsString() {
        return this.element.getAsString();
    }

    @Override
    public String toJson() {
        return this.element.toString();
    }

    @Override
    public CompoundElement clone() {
        return new JsonCompoundElement(GSON.fromJson(GSON.toJson(this.element, JsonElement.class), JsonElement.class));
    }

    public static JsonCompoundElement toCompound(JsonElement element) {
        if (element == null)
            return JsonCompoundNull.NULL;

        if (element instanceof JsonPrimitive primitive)
            return new JsonCompoundPrimitive(primitive);

        if (element instanceof JsonNull)
            return JsonCompoundNull.NULL;

        if (element instanceof JsonObject object)
            return new JsonCompoundObject(object);

        if (element instanceof JsonArray array)
            return new JsonCompoundArray(array);

        throw new IllegalStateException("Unknown JsonElement Type: " + element);
    }

    public static JsonElement fromCompound(JsonCompoundElement element) {
        if (element == null)
            return JsonNull.INSTANCE;

        if (element instanceof JsonCompoundPrimitive primitive)
            return primitive.getPrimitive();

        if (element instanceof JsonCompoundNull)
            return JsonNull.INSTANCE;

        if (element instanceof JsonCompoundObject object)
            return object.getObject();

        if (element instanceof JsonCompoundArray array)
            return array.getArray();

        throw new IllegalStateException("Unknown JsonCompoundElement Type: " + element);
    }
}
