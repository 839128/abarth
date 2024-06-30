package org.miaixz.bus.core.center.map;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ObjectKit;
import org.miaixz.bus.core.xyz.RandomKit;
import org.miaixz.bus.core.xyz.SerializeKit;

import java.util.HashMap;

public class TolerantMapTest {

    @Test
    public void testSerialize() {
        TolerantMap<String, String> map = TolerantMap.of(new HashMap<>(), "default");
        map.put("monday", "星期一");
        map.put("tuesday", "星期二");

        final byte[] bytes = SerializeKit.serialize(map);
        final TolerantMap<String, String> serializedMap = SerializeKit.deserialize(bytes);
        assert serializedMap != map;
        assert map.equals(serializedMap);
    }

    @Test
    public void testClone() {
        TolerantMap<String, String> map = TolerantMap.of(new HashMap<>(), "default");
        map.put("monday", "星期一");
        map.put("tuesday", "星期二");

        final TolerantMap<String, String> clonedMap = ObjectKit.clone(map);
        assert clonedMap != map;
        assert map.equals(clonedMap);
    }

    @Test
    public void testGet() {
        TolerantMap<String, String> map = TolerantMap.of(new HashMap<>(), "default");
        map.put("monday", "星期一");
        map.put("tuesday", "星期二");

        assert "星期二".equals(map.get("tuesday"));
        assert "default".equals(map.get(RandomKit.randomStringLower(6)));
    }

}
