package org.miaixz.bus.core.center.list;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.MapProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapProxyTest {

    @Test
    public void mapProxyTest() {
        final Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        final MapProxy mapProxy = new MapProxy(map);
        final Integer b = mapProxy.getInt("b");
        Assertions.assertEquals(Integer.valueOf(2), b);

        final Set<Object> keys = mapProxy.keySet();
        Assertions.assertFalse(keys.isEmpty());

        final Set<Entry<Object, Object>> entrys = mapProxy.entrySet();
        Assertions.assertFalse(entrys.isEmpty());
    }

    @Test
    public void classProxyTest() {
        final Student student = MapProxy.of(new HashMap<>()).toProxyBean(Student.class);
        student.setName("小明").setAge(18);
        Assertions.assertEquals(student.getAge(), 18);
        Assertions.assertEquals(student.getName(), "小明");
    }

    private interface Student {
        String getName();

        Student setName(String name);

        int getAge();

        Student setAge(int age);
    }

}
