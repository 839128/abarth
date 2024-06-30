package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.reflect.kotlin.KParameter;
import org.miaixz.bus.core.lang.reflect.kotlin.TestKBean;

import java.util.HashMap;
import java.util.List;

public class KotlinKitTest {

    @Test
    void isKotlinClassTest() {
        boolean kotlinClass = KotlinKit.isKotlinClass(TestKBean.class);
        Assertions.assertTrue(kotlinClass);

        kotlinClass = KotlinKit.isKotlinClass(KotlinKitTest.class);
        Assertions.assertFalse(kotlinClass);
    }

    @Test
    void getConstructorTest() {
        final List<?> constructors = KotlinKit.getConstructors(TestKBean.class);
        Assertions.assertEquals(1, constructors.size());

        Assertions.assertEquals("kotlin.reflect.jvm.internal.KFunctionImpl",
                constructors.get(0).getClass().getName());
    }

    @Test
    void getParametersTest() {
        final List<?> constructors = KotlinKit.getConstructors(TestKBean.class);

        final List<KParameter> parameters = KotlinKit.getParameters(constructors.get(0));
        Assertions.assertEquals(3, parameters.size());

        Assertions.assertEquals("id", parameters.get(0).getName());
        Assertions.assertEquals("name", parameters.get(1).getName());
        Assertions.assertEquals("country", parameters.get(2).getName());
    }

    @Test
    void newInstanceTest() {
        final HashMap<String, Object> argsMap = new HashMap<>();
        argsMap.put("country", "中国");
        argsMap.put("age", 18);
        argsMap.put("id", "VampireAchao");

        final TestKBean testKBean = KotlinKit.newInstance(TestKBean.class, argsMap);

        Assertions.assertEquals("VampireAchao", testKBean.getId());
        Assertions.assertEquals("中国", testKBean.getCountry());
        Assertions.assertNull(testKBean.getName());
    }

}
