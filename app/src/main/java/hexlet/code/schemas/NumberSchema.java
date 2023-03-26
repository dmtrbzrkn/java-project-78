package hexlet.code.schemas;

public class NumberSchema extends BaseSchema {
    public NumberSchema positive() {
        addRequirements(number -> number == null
                || number instanceof Integer
                && (Integer) number > 0);
        return this;
    }

    public NumberSchema range(int start, int end) {
        addRequirements(number -> number == null
                || number instanceof Integer
                && (Integer) number >= start
                && (Integer) number <= end);
        return this;
    }

    public NumberSchema required() {
        addRequirements(number -> number instanceof Integer);
        return this;
    }
}
