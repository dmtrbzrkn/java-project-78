package hexlet.code;

import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidatorTest {
    @Test
    public void testStringValidator() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(5));

        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(5));

        schema.minLength(5);
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("wh"));

        schema.contains("wh");
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("waat does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));

    }

    @Test
    public void testNumberValidator() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid(5));

        assertFalse(schema.required().isValid(null));
        assertFalse(schema.required().isValid("wh"));
        assertTrue(schema.required().isValid(10));

        assertTrue(schema.positive().isValid(10));
        assertFalse(schema.positive().isValid(0));
        assertFalse(schema.positive().isValid(-10));

        assertTrue(schema.range(5, 10).isValid(5));
        assertTrue(schema.range(5, 10).isValid(10));
        assertFalse(schema.range(5, 10).isValid(11));
        assertFalse(schema.range(5, 10).isValid(-5));

    }
    @Test
    public void testMapValidator() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid(5));

        schema.required();
        assertTrue(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid("wh"));
        assertFalse(schema.isValid(10));

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");

        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));

    }
}
