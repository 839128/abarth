package org.aoju.bus.core.toolkit;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeKitTest {

    @Test
    public void getEleTypeTest() {
        Method method = ReflectKit.getMethod(TestClass.class, "getList");
        Type type = TypeKit.getReturnType(method);
        Assert.assertEquals("java.util.List<java.lang.String>", type.toString());

        Type type2 = TypeKit.getTypeArgument(type);
        Assert.assertEquals(String.class, type2);
    }

    @Test
    public void getParamTypeTest() {
        Method method = ReflectKit.getMethod(TestClass.class, "intTest", Integer.class);
        Type type = TypeKit.getParamType(method, 0);
        Assert.assertEquals(Integer.class, type);

        Type returnType = TypeKit.getReturnType(method);
        Assert.assertEquals(Integer.class, returnType);
    }

    public static class TestClass {
        public List<String> getList() {
            return new ArrayList<>();
        }

        public Integer intTest(Integer integer) {
            return 1;
        }
    }

}
