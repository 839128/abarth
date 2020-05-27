package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * {@link ClassKit} 单元测试
 */
public class ClassKitTest {

    @Test
    public void getClassNameTest() {
        String className = ClassKit.getClassName(ClassKit.class, false);
        Assertions.assertEquals("org.aoju.bus.core.utils.ClassKit", className);

        String simpleClassName = ClassKit.getClassName(ClassKit.class, true);
        Assertions.assertEquals("ClassKit", simpleClassName);
    }

    @Test
    public void getPublicMethod() {
        Method superPublicMethod = ClassKit.getPublicMethod(TestSubClass.class, "publicMethod");
        Assertions.assertNotNull(superPublicMethod);
        Method superPrivateMethod = ClassKit.getPublicMethod(TestSubClass.class, "privateMethod");
        Assertions.assertNull(superPrivateMethod);

        Method publicMethod = ClassKit.getPublicMethod(TestSubClass.class, "publicSubMethod");
        Assertions.assertNotNull(publicMethod);
        Method privateMethod = ClassKit.getPublicMethod(TestSubClass.class, "privateSubMethod");
        Assertions.assertNull(privateMethod);
    }

    @Test
    public void getDeclaredMethod() {
        Method noMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "noMethod");
        Assertions.assertNull(noMethod);

        Method privateMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "privateMethod");
        Assertions.assertNotNull(privateMethod);
        Method publicMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "publicMethod");
        Assertions.assertNotNull(publicMethod);

        Method publicSubMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "publicSubMethod");
        Assertions.assertNotNull(publicSubMethod);
        Method privateSubMethod = ClassKit.getDeclaredMethod(TestSubClass.class, "privateSubMethod");
        Assertions.assertNotNull(privateSubMethod);

    }

    @Test
    public void getDeclaredField() {
        Field noField = ClassKit.getDeclaredField(TestSubClass.class, "noField");
        Assertions.assertNull(noField);

        // 获取不到父类字段
        Field field = ClassKit.getDeclaredField(TestSubClass.class, "field");
        Assertions.assertNull(field);

        Field subField = ClassKit.getDeclaredField(TestSubClass.class, "subField");
        Assertions.assertNotNull(subField);
    }

    @Test
    public void getClassPathTest() {
        String classPath = ClassKit.getClassPath();
        Assertions.assertNotNull(classPath);
    }

    @Test
    public void getShortClassNameTest() {
        String className = "org.aoju.bus.core.utils.StringKit";
        String result = ClassKit.getShortClassName(className);
        Assertions.assertEquals("o.a.b.c.u.StringKit", result);
    }

    @Test
    public void loadClassTest() {
        String name = ClassKit.loadClass("java.lang.Thread.State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);

        name = ClassKit.loadClass("java.lang.Thread$State").getName();
        Assertions.assertEquals("java.lang.Thread$State", name);
    }

    static class TestClass {
        protected String field;
        private String privateField;

        private void privateMethod() {
        }

        public void publicMethod() {
        }
    }

    class TestSubClass extends TestClass {
        private String subField;

        private void privateSubMethod() {
        }

        public void publicSubMethod() {
        }

    }

}
