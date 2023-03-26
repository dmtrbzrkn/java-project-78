package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema {
    public MapSchema required() {
        addRequirements(map -> map instanceof Map);
        return this;
    }

    public MapSchema sizeof(int size) {
        addRequirements(map -> map == null
                || map instanceof Map
                && ((Map<?, ?>) map).size() == size);
        return this;
    }
}
