package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema {
    private List<Predicate<Object>> requirements = new ArrayList<>();

    protected final void addRequirements (Predicate<Object> predicate) {
        requirements.add(predicate);
    }

    public boolean isValid (Object data) {
        for (Predicate<Object> predicate : requirements) {
            if (!predicate.test(data)) {
                return false;
            }
        }
        return true;
    }
}
