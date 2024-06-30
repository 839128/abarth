package org.miaixz.bus.core.center.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class FunctionMapTest {

    @Test
    public void putGetTest() {
        final FunctionMap<Object, Object> map = new FunctionMap<>(HashMap::new,
                (key) -> key.toString().toLowerCase(),
                (value) -> value.toString().toUpperCase());

        map.put("aaa", "b");
        map.put("BBB", "c");

        Assertions.assertEquals("B", map.get("aaa"));
        Assertions.assertEquals("C", map.get("bbb"));
    }

}
