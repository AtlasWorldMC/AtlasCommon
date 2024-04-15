package fr.atlasworld.common.compound.json;

import com.google.gson.JsonPrimitive;
import fr.atlasworld.common.compound.CompoundPrimitive;

public class JsonCompoundPrimitive extends JsonCompoundElement implements CompoundPrimitive {
    private final JsonPrimitive primitive;

    public JsonCompoundPrimitive(JsonPrimitive element) {
        super(element);
        this.primitive = element;
    }

    @Override
    public boolean isBoolean() {
        return this.primitive.isBoolean();
    }

    @Override
    public boolean isDouble() {
        return this.primitive.isNumber();
    }

    @Override
    public boolean isLong() {
        return this.primitive.isNumber();
    }

    @Override
    public boolean isInt() {
        return this.primitive.isNumber();
    }

    @Override
    public boolean isByte() {
        return this.primitive.isNumber();
    }

    @Override
    public boolean isByteArray() {
        return this.primitive.isString();
    }

    @Override
    public boolean isDate() {
        return this.primitive.isNumber();
    }

    @Override
    public boolean isChar() {
        return this.primitive.isString();
    }

    @Override
    public boolean isString() {
        return this.primitive.isString();
    }

    @Override
    public JsonCompoundPrimitive clone() {
        return (JsonCompoundPrimitive) super.clone();
    }

    public JsonPrimitive getPrimitive() {
        return primitive;
    }
}
