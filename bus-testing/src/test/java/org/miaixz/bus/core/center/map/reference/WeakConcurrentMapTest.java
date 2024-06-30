package org.miaixz.bus.core.center.map.reference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeakConcurrentMapTest {

    @Test
    public void testWeakConcurrentMap() {
        final WeakConcurrentMap<String, String> map = new WeakConcurrentMap<>();

        // Test if the map is initially empty
        Assertions.assertTrue(map.isEmpty());

        // Test if the map can store and retrieve values correctly
        map.put("key1", "value1");
        Assertions.assertEquals("value1", map.get("key1"));

        map.putIfAbsent("key2", "value2");
        // Test if the map can handle concurrent modifications
        new Thread(() -> {
            map.put("key2", "value2");
        }).start();

        // Test if the map has correctly stored the value from the concurrent modification
        Assertions.assertEquals("value2", map.get("key2"));

        Assertions.assertTrue(map.containsKey("key1"));
        Assertions.assertTrue(map.containsKey("key2"));

        // null值
        String s = map.computeIfAbsent("key3", key -> null);
        Assertions.assertNull(s);

        // 允许key为null
        s = map.computeIfAbsent(null, key -> null);
        Assertions.assertNull(s);
    }


    @Test
    void computeIfAbsentTest() {
        final WeakConcurrentMap<String, String> map = new WeakConcurrentMap<>();
        final String value = map.computeIfAbsent("key1", key -> new String("value1"));
        Assertions.assertNotNull(value);
    }

}
