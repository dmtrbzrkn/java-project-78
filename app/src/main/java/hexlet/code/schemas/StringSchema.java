package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema contains(String substring) {
        addRequirements(string -> (string == null)
                || (string instanceof String)
                && ((String) string).contains(substring));
        return this;
    }

    public StringSchema minLength(int length) {
        addRequirements(string -> string == null
                || string instanceof String
                && ((String) string).length() >= length);
        return this;
    }

    public StringSchema required() {
        this.addRequirements(string -> string instanceof String
                && !((String) string).isBlank());
        return this;
    }
}
