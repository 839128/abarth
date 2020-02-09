package org.aoju.bus.core.convert;

import lombok.Data;
import org.aoju.bus.core.map.MapBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map转换单元测试
 */
public class MapConvertTest {

    @Test
    public void beanToMapTest() {
        User user = new User();
        user.setName("AAA");
        user.setAge(45);

        HashMap<?, ?> map = Convert.convert(HashMap.class, user);
        Assertions.assertEquals("AAA", map.get("name"));
        Assertions.assertEquals(45, map.get("age"));
    }

    @Test
    public void mapToMapTest() {
        Map<String, Object> srcMap = MapBuilder.create(new HashMap<String, Object>()).put("name", "AAA").put("age", 45).map();

        LinkedHashMap<?, ?> map = Convert.convert(LinkedHashMap.class, srcMap);
        Assertions.assertEquals("AAA", map.get("name"));
        Assertions.assertEquals(45, map.get("age"));
    }

    @Data
    public static class User {
        private String name;
        private int age;
    }

}
