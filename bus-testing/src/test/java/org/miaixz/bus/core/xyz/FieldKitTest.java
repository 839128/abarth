package org.miaixz.bus.core.xyz;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.reflect.ReflectTestBeans;

import java.lang.reflect.Field;

public class FieldKitTest {
    @Test
    public void getFieldTest() {
        // 能够获取到父类字段
        final Field privateField = FieldKit.getField(ReflectTestBeans.TestSubClass.class, "privateField");
        Assertions.assertNotNull(privateField);
    }

    @Test
    public void getFieldsTest() {
        // 能够获取到父类字段
        final Field[] fields = FieldKit.getFields(ReflectTestBeans.TestSubClass.class);
        Assertions.assertEquals(4, fields.length);
    }

    @Test
    public void setFieldTest() {
        final ReflectTestBeans.AClass testClass = new ReflectTestBeans.AClass();
        FieldKit.setFieldValue(testClass, "a", "111");
        Assertions.assertEquals(111, testClass.getA());
    }

    @Test
    public void getDeclaredField() {
        final Field noField = FieldKit.getField(ReflectTestBeans.TestSubClass.class, "noField");
        Assertions.assertNull(noField);

        // 获取不到父类字段
        final Field field = FieldKit.getDeclearField(ReflectTestBeans.TestSubClass.class, "field");
        Assertions.assertNull(field);

        final Field subField = FieldKit.getField(ReflectTestBeans.TestSubClass.class, "subField");
        Assertions.assertNotNull(subField);
    }

    @Test
    void getFieldsValueTest() {
        final TestBean testBean = new TestBean();
        testBean.setA("A");
        testBean.setB(1);

        final Object[] fieldsValue = FieldKit.getFieldsValue(testBean);
        Assertions.assertEquals(2, fieldsValue.length);
        Assertions.assertEquals("A", fieldsValue[0]);
        Assertions.assertEquals(1, fieldsValue[1]);
    }

    @Test
    void getFieldsValueTest2() {
        final TestBean testBean = new TestBean();
        testBean.setA("A");
        testBean.setB(1);

        final Object[] fieldsValue = FieldKit.getFieldsValue(testBean, (field -> field.getName().equals("a")));
        Assertions.assertEquals(1, fieldsValue.length);
        Assertions.assertEquals("A", fieldsValue[0]);
    }

    @Data
    static class TestBean {
        private String a;
        private int b;
    }

}
