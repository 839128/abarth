package org.aoju.bus.core.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeUtilsTest {

    @Test
    public void getEleTypeTest() {
        Method method = ReflectUtils.getMethod(TestClass.class, "getList");
        Type type = TypeUtils.getReturnType(method);
        Assertions.assertEquals("java.util.List<java.lang.String>", type.toString());

        Type type2 = TypeUtils.getTypeArgument(type);
        Assertions.assertEquals(String.class, type2);
    }

    @Test
    public void getParamTypeTest() {
        Method method = ReflectUtils.getMethod(TestClass.class, "intTest", Integer.class);
        Type type = TypeUtils.getParamType(method, 0);
        Assertions.assertEquals(Integer.class, type);

        Type returnType = TypeUtils.getReturnType(method);
        Assertions.assertEquals(Integer.class, returnType);
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
