package hexlet.code;

import hexlet.code.schemas.BaseSchema;
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
    void testStringValidatorWithOutRequired() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(5));

    }

    @Test
    void testStringValidatorWithRequired() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(5));
    }

    @Test
    void testStringValidatorMinLength() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        schema.minLength(5);
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("wh"));
    }

    @Test
    void testStringValidatorContains() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        schema.contains("wh");
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("waat does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    void testNumberValidatorWithOutRequired() {
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid(5));
    }

    @Test
    void testNumberValidatorWithRequired() {
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

        assertFalse(schema.required().isValid(null));
        assertFalse(schema.required().isValid("wh"));
        assertTrue(schema.required().isValid(10));
    }

    @Test
    void testNumberValidatorPositive() {
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

        assertTrue(schema.positive().isValid(10));
        assertFalse(schema.positive().isValid(0));
        assertFalse(schema.positive().isValid(-10));
    }

    @Test
    void testNumberValidatorRange() {
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

        assertTrue(schema.range(5, 10).isValid(5));
        assertTrue(schema.range(5, 10).isValid(10));
        assertFalse(schema.range(5, 10).isValid(11));
        assertFalse(schema.range(5, 10).isValid(-5));
    }

    @Test
    public void testMapValidatorSizeOf() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");

        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));

    }
    @Test
    void testMapValidatorWithOutRequired() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("abc"));
        assertTrue(schema.isValid(5));
    }
    @Test
    void testMapValidatorWithRequired() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        schema.required();
        assertTrue(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid("wh"));
        assertFalse(schema.isValid(10));
    }
    @Test
    void testValidatorShape1() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().required().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Dmitrii");
        human1.put("age", 29);
        assertTrue(schema.isValid(human1));

        Map<String, Object> numan2 = new HashMap<>();
        numan2.put("name", "Natalia");
        numan2.put("age", -25);
        assertFalse(schema.isValid(numan2));
    }
    @Test
    void testValidatorShape2() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().required().range(1, 7));
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "");
        human1.put("age", 111);
        assertFalse(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Ivan");
        human2.put("age", 7);
        assertTrue(schema.isValid(human2));
    }
    @Test
    void testValidatorShape3() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required().contains("trii"));
        schemas.put("age", validator.number().required());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Dmitrii");
        human1.put("quantity", "30");
        assertFalse(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Dmitrii");
        human2.put("age", 30);
        assertTrue(schema.isValid(human2));
    }
    @Test
    void testValidatorShape4() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required().contains("lia"));
        schemas.put("age", validator.number().required());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Natasha");
        human1.put("quantity", "111");
        assertFalse(schema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Natalia");
        human2.put("age", 41);
        assertTrue(schema.isValid(human2));
    }
}
