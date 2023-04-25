package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {
    @Override
    public boolean isValidType(Object object) {
        return object instanceof Integer;
    }

    public NumberSchema positive() {
        addRequirements(POSITIVE, (number -> number == null
                || number instanceof Integer
                && (Integer) number > 0));
        return this;
    }

    public NumberSchema range(int start, int end) {
        addRequirements(RANGE, (number -> number == null
                || number instanceof Integer
                && (Integer) number >= start
                && (Integer) number <= end));
        return this;
    }

    public NumberSchema required() {
        addRequirements(REQUIRED, (number -> number instanceof Integer));
        return this;
    }
}
