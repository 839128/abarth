package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.bean.ExamInfoDict;
import org.aoju.bus.core.toolkit.ClassKitTest.TestSubClass;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类单元测试
 */
public class ReflectKitTest {

    @Test
    public void getMethodsTest() {
        Method[] methods = ReflectKit.getMethods(ExamInfoDict.class);
        Assert.assertEquals(22, methods.length);

        //过滤器测试
        methods = ReflectKit.getMethods(ExamInfoDict.class, t -> Integer.class.equals(t.getReturnType()));

        Assert.assertEquals(4, methods.length);
        final Method method = methods[0];
        Assert.assertNotNull(method);

        //null过滤器测试
        methods = ReflectKit.getMethods(ExamInfoDict.class, null);

        Assert.assertEquals(22, methods.length);
        final Method method2 = methods[0];
        Assert.assertNotNull(method2);
    }

    @Test
    public void getMethodTest() {
        Method method = ReflectKit.getMethod(ExamInfoDict.class, "getId");
        Assert.assertEquals("getId", method.getName());
        Assert.assertEquals(0, method.getParameterTypes().length);

        method = ReflectKit.getMethod(ExamInfoDict.class, "getId", Integer.class);
        Assert.assertEquals("getId", method.getName());
        Assert.assertEquals(1, method.getParameterTypes().length);
    }

    @Test
    public void getMethodIgnoreCaseTest() {
        Method method = ReflectKit.getMethodIgnoreCase(ExamInfoDict.class, "getId");
        Assert.assertEquals("getId", method.getName());
        Assert.assertEquals(0, method.getParameterTypes().length);

        method = ReflectKit.getMethodIgnoreCase(ExamInfoDict.class, "GetId");
        Assert.assertEquals("getId", method.getName());
        Assert.assertEquals(0, method.getParameterTypes().length);

        method = ReflectKit.getMethodIgnoreCase(ExamInfoDict.class, "setanswerIs", Integer.class);
        Assert.assertEquals("setAnswerIs", method.getName());
        Assert.assertEquals(1, method.getParameterTypes().length);
    }

    @Test
    public void getFieldTest() {
        // 能够获取到父类字段
        Field privateField = ReflectKit.getField(TestSubClass.class, "privateField");
        Assert.assertNotNull(privateField);
    }

    @Test
    public void getFieldsTest() {
        // 能够获取到父类字段
        final Field[] fields = ReflectKit.getFields(TestSubClass.class);
        Assert.assertEquals(4, fields.length);
    }

    @Test
    public void setFieldTest() {
        TestClass testClass = new TestClass();
        ReflectKit.setFieldValue(testClass, "a", "111");
        Assert.assertEquals(111, testClass.getA());
    }

    @Test
    public void invokeTest() {
        TestClass testClass = new TestClass();
        ReflectKit.invoke(testClass, "setA", 10);
        Assert.assertEquals(10, testClass.getA());
    }

    static class TestClass {
        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

}
