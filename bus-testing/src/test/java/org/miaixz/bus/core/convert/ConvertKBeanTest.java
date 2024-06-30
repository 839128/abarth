package org.miaixz.bus.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.reflect.kotlin.TestKBean;

import java.util.HashMap;

public class ConvertKBeanTest {

    @Test
    void mapToBeanTest() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("country", "中国");
        map.put("age", 18);
        map.put("id", "VampireAchao");

        final TestKBean testKBean = Convert.convert(TestKBean.class, map);

        Assertions.assertEquals("VampireAchao", testKBean.getId());
        Assertions.assertEquals("中国", testKBean.getCountry());
        Assertions.assertNull(testKBean.getName());
    }

}
