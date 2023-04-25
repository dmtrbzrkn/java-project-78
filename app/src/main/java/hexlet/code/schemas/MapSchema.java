package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {
    @Override
    public boolean isValidType(Object object) {
        return object instanceof Map;
    }

    public MapSchema required() {
        addRequirements(REQUIRED, (map -> map instanceof Map));
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        addRequirements(SHAPE, (map -> map == null
                || map instanceof Map
                && schemas.entrySet().stream()
                .allMatch(schema -> schema.getValue()
                        .isValid(((Map<?, ?>) map)
                                .get(schema.getKey())))));

        return this;
    }

    public MapSchema sizeof(int size) {
        addRequirements(SIZE_OF, (map -> map == null
                || map instanceof Map
                && ((Map<?, ?>) map).size() == size));
        return this;
    }
}
