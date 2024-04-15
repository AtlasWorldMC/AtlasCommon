package fr.atlasworld.common.compound.json;

import com.google.gson.JsonNull;
import fr.atlasworld.common.compound.CompoundNull;

public class JsonCompoundNull extends JsonCompoundElement implements CompoundNull {
    public static final JsonCompoundNull NULL = new JsonCompoundNull(JsonNull.INSTANCE);

    private JsonCompoundNull(JsonNull element) {
        super(element);
    }

    public JsonCompoundNull clone() {
        return NULL;
    }
}
