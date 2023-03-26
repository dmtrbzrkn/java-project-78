package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema {
    private List<Predicate<Object>> requirements = new ArrayList<>();

    public boolean isValid(Object object) {
        for (Predicate<Object> predicate : requirements) {
            if (!(predicate.test(object))) {
                return false;
            }
        }
        return true;
    }
    protected final void addRequirements(Predicate<Object> predicate) {
        requirements.add(predicate);
    }
}
