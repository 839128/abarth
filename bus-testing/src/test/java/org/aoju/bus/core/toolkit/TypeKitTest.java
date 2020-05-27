package org.aoju.bus.core.toolkit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeKitTest {

    @Test
    public void getEleTypeTest() {
        Method method = ReflectKit.getMethod(TestClass.class, "getList");
        Type type = TypeKit.getReturnType(method);
        Assertions.assertEquals("java.util.List<java.lang.String>", type.toString());

        Type type2 = TypeKit.getTypeArgument(type);
        Assertions.assertEquals(String.class, type2);
    }

    @Test
    public void getParamTypeTest() {
        Method method = ReflectKit.getMethod(TestClass.class, "intTest", Integer.class);
        Type type = TypeKit.getParamType(method, 0);
        Assertions.assertEquals(Integer.class, type);

        Type returnType = TypeKit.getReturnType(method);
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
