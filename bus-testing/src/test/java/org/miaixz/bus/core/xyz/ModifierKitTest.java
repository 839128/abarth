package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.miaixz.bus.core.lang.EnumMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ModifierKitTest {

    private static void ddd() {
    }

    @Test
    public void hasModifierTest() throws NoSuchMethodException {
        final Method method = ModifierKitTest.class.getDeclaredMethod("ddd");
        Assertions.assertTrue(ModifierKit.hasModifier(method, EnumMap.Modifier.PRIVATE));
        Assertions.assertTrue(ModifierKit.hasModifier(method,
                EnumMap.Modifier.PRIVATE,
                EnumMap.Modifier.STATIC)
        );
    }

    @Test
    @EnabledForJreRange(max = org.junit.jupiter.api.condition.JRE.JAVA_8)
    void removeFinalModifyTest() {
        final String fieldName = "DIALECTS";
        final Field field = FieldKit.getField(JdbcDialects.class, fieldName);
        ModifierKit.removeFinalModify(field);
    }

    @Test
    @EnabledForJreRange(max = org.junit.jupiter.api.condition.JRE.JAVA_8)
    public void setFinalFieldValueTest() {
        final String fieldName = "DIALECTS";
        final List<Number> dialects =
                Arrays.asList(
                        1,
                        2,
                        3,
                        99
                );
        final Field field = FieldKit.getField(JdbcDialects.class, fieldName);
        ModifierKit.removeFinalModify(field);
        FieldKit.setFieldValue(JdbcDialects.class, fieldName, dialects);

        Assertions.assertEquals(dialects, FieldKit.getFieldValue(JdbcDialects.class, fieldName));
    }

    public static class JdbcDialects {
        private static final List<Number> DIALECTS =
                Arrays.asList(1L, 2L, 3L);
    }

}
