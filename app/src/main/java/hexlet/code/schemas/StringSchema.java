package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    @Override
    public boolean isValidType(Object object) {
        return object instanceof String;
    }

    public StringSchema contains(String substring) {
        addRequirements(CONTAINS, (string -> (string == null)
                || (string instanceof String)
                && ((String) string).contains(substring)));
        return this;
    }

    public StringSchema minLength(int length) {
        addRequirements(MIN_LENGTH, (string -> string == null
                || string instanceof String
                && ((String) string).length() >= length));
        return this;
    }

    public StringSchema required() {
        setRequired(true);
        this.addRequirements(STRING_REQUIRED, (string -> string instanceof String
                && !((String) string).isBlank()));
        return this;
    }
}
