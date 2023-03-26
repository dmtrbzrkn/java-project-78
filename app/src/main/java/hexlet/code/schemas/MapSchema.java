package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema {
    public MapSchema required() {
        addRequirements(map -> map instanceof Map);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        addRequirements(map -> map == null
                || map instanceof Map
                && schemas.entrySet().stream()
                .allMatch(schema -> schema.getValue()
                        .isValid(((Map<?, ?>) map)
                                .get(schema.getKey()))));

        return this;
    }

    public MapSchema sizeof(int size) {
        addRequirements(map -> map == null
                || map instanceof Map
                && ((Map<?, ?>) map).size() == size);
        return this;
    }
}
