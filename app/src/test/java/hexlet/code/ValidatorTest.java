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
    public void testStringValidator() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

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
        Validator validator = new Validator();
        NumberSchema schema = validator.number();

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
        Validator validator = new Validator();
        MapSchema schema = validator.map();

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
    @Test
    public void testValidatorShape() {

        Validator validatorOne = new Validator();
        MapSchema schema = validatorOne.map();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", validatorOne.string().required());
        schemas1.put("age", validatorOne.number().required().positive());
        schema.shape(schemas1);

        Map<String, Object> humanOne = new HashMap<>();
        humanOne.put("name", "Dmitrii");
        humanOne.put("age", 29);
        assertTrue(schema.isValid(humanOne));

        Map<String, Object> numanTwo = new HashMap<>();
        numanTwo.put("name", "Natalia");
        numanTwo.put("age", -25);
        assertFalse(schema.isValid(numanTwo));

        Validator validatorTwo = new Validator();
        MapSchema schema2 = validatorTwo.map();

        Map<String, BaseSchema> schemas2 = new HashMap<>();
        schemas2.put("name", validatorTwo.string().required());
        schemas2.put("age", validatorTwo.number().required().range(1, 7));
        schema2.shape(schemas2);

        Map<String, Object> humanThree = new HashMap<>();
        humanThree.put("name", "");
        humanThree.put("age", 111);
        assertFalse(schema2.isValid(humanThree));

        Map<String, Object> humanFour = new HashMap<>();
        humanFour.put("name", "Ivan");
        humanFour.put("age", 7);
        assertTrue(schema2.isValid(humanFour));


        Validator validatorTree = new Validator();
        MapSchema schema3 = validatorTree.map();

        Map<String, BaseSchema> schemas3 = new HashMap<>();
        schemas3.put("name", validatorTree.string().required().contains("trii"));
        schemas3.put("age", validatorTree.number().required());
        schema3.shape(schemas3);

        Map<String, Object> humanFive = new HashMap<>();
        humanFive.put("name", "Dmitrii");
        humanFive.put("quantity", "30");
        assertFalse(schema3.isValid(humanFive));

        Map<String, Object> humanSix = new HashMap<>();
        humanSix.put("name", "Dmitrii");
        humanSix.put("age", 30);
        assertTrue(schema3.isValid(humanSix));


        Validator validatorFour = new Validator();
        MapSchema schema4 = validatorFour.map();

        Map<String, BaseSchema> schemas4 = new HashMap<>();
        schemas4.put("name", validatorFour.string().required().contains("lia"));
        schemas4.put("age", validatorFour.number().required());
        schema4.shape(schemas4);

        Map<String, Object> humanSeven = new HashMap<>();
        humanSeven.put("name", "Natasha");
        humanSeven.put("quantity", "111");
        assertFalse(schema4.isValid(humanSeven));

        Map<String, Object> human8 = new HashMap<>();
        human8.put("name", "Natalia");
        human8.put("age", 41);
        assertTrue(schema4.isValid(human8));
    }
}
