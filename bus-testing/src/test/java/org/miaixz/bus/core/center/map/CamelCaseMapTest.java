package org.miaixz.bus.core.center.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.SerializeKit;

public class CamelCaseMapTest {

    @Test
    public void caseInsensitiveMapTest() {
        final CamelCaseMap<String, String> map = new CamelCaseMap<>();
        map.put("customKey", "OK");
        Assertions.assertEquals("OK", map.get("customKey"));
        Assertions.assertEquals("OK", map.get("custom_key"));
    }

    @Test
    public void caseInsensitiveLinkedMapTest() {
        final CamelCaseLinkedMap<String, String> map = new CamelCaseLinkedMap<>();
        map.put("customKey", "OK");
        Assertions.assertEquals("OK", map.get("customKey"));
        Assertions.assertEquals("OK", map.get("custom_key"));
    }

    @Test
    public void serializableKeyFuncTest() {
        final CamelCaseMap<String, String> map = new CamelCaseMap<>();
        map.put("serializable_key", "OK");
        final CamelCaseMap<String, String> deSerializableMap = SerializeKit.deserialize(SerializeKit.serialize(map));
        Assertions.assertEquals("OK", deSerializableMap.get("serializable_key"));
        Assertions.assertEquals("OK", deSerializableMap.get("serializableKey"));
        deSerializableMap.put("serializable_func", "OK");
        Assertions.assertEquals("OK", deSerializableMap.get("serializable_func"));
        Assertions.assertEquals("OK", deSerializableMap.get("serializableFunc"));
    }

}
