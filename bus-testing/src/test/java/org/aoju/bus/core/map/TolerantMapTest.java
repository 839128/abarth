package org.aoju.bus.core.map;

import org.aoju.bus.core.toolkit.ObjectKit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TolerantMapTest {

    private final TolerantMap<String, String> map = TolerantMap.of(new HashMap<>(), "default");

    @BeforeEach
    public void before() {
        map.put("monday", "星期一");
        map.put("tuesday", "星期二");
    }

    @Test
    public void testSerialize() {
        byte[] bytes = ObjectKit.serialize(map);
        TolerantMap<String, String> serializedMap = ObjectKit.deserialize(bytes);
        assert serializedMap != map;
        assert map.equals(serializedMap);
    }

    @Test
    public void testClone() {
        TolerantMap<String, String> clonedMap = ObjectKit.clone(map);
        assert clonedMap != map;
        assert map.equals(clonedMap);
    }

}
