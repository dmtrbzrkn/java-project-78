package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;


public abstract class BaseSchema {
    public static final String STRING_REQUIRED = "str_required";
    public static final String NUM_REQUIRED = "num_required";
    public static final String MAP_REQUIRED = "map_required";
    public static final String SHAPE = "shape";
    public static final String SIZE_OF = "sizeOf";
    public static final String POSITIVE = "positive";
    public static final String RANGE = "range";
    public static final String CONTAINS = "contains";
    public static final String MIN_LENGTH = "minLength";
    private Map<String, Predicate<Object>> requirements = new HashMap<>();
    private boolean isRequired;

    public final void setRequired(boolean required) {
        isRequired = required;
    }

    public final boolean isValid(Object object) {
        if (!isRequired && !isValidType(object)) {
            return true;
        } else if (isRequired && !isValidType(object)) {
            return false;
        } else {
            for (Map.Entry<String, Predicate<Object>> entry : requirements.entrySet()) {
                if (!entry.getValue().test(object)) {
                    return false;
                }
            }
            return true;
        }
    }

    abstract boolean isValidType(Object object);

    protected final void addRequirements(String typeOfValidation, Predicate<Object> predicate) {
        requirements.put(typeOfValidation, predicate);
    }

}
