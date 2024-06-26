package org.miaixz.bus.core.center.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.multi.ListValueMap;
import org.miaixz.bus.core.center.map.multi.MultiValueMap;
import org.miaixz.bus.core.xyz.StringKit;

import java.util.*;

public class ListValueMapTest {

    @Test
    public void putTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertNull(map.put(1, Arrays.asList("a", "b")));
        Collection<String> collection = map.put(1, Arrays.asList("c", "d"));
        Assertions.assertEquals(Arrays.asList("a", "b"), collection);
    }

    @Test
    public void putAllTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Map<Integer, Collection<String>> source = new HashMap<>();
        source.put(1, Arrays.asList("a", "b", "c"));
        map.putAll(source);
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), map.get(1));
    }

    @Test
    public void putValueTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertTrue(map.putValue(1, "a"));
        Assertions.assertTrue(map.putValue(1, "b"));
        Assertions.assertTrue(map.putValue(1, "c"));
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), map.get(1));
    }

    @Test
    public void putAllValueTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertTrue(map.putAllValues(1, Arrays.asList("a", "b", "c")));
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), map.get(1));

        Map<Integer, Collection<String>> source = new HashMap<>();
        Assertions.assertTrue(map.putValue(1, "e"));
        Assertions.assertTrue(map.putValue(1, "f"));
        map.putAllValues(source);
        Assertions.assertEquals(Arrays.asList("a", "b", "c", "e", "f"), map.get(1));
    }

    @Test
    public void putValuesTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertTrue(map.putValues(1, "a", "b", "c"));
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), map.get(1));
    }

    @Test
    public void removeValueTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        Assertions.assertFalse(map.removeValue(1, "d"));
        Assertions.assertTrue(map.removeValue(1, "c"));
        Assertions.assertEquals(Arrays.asList("a", "b"), map.get(1));
    }

    @Test
    public void removeAllValuesTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        Assertions.assertFalse(map.removeAllValues(1, Arrays.asList("e", "f")));
        Assertions.assertTrue(map.removeAllValues(1, Arrays.asList("b", "c")));
        Assertions.assertEquals(Collections.singletonList("a"), map.get(1));
    }

    @Test
    public void removeValuesTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        Assertions.assertFalse(map.removeValues(1, "e", "f"));
        Assertions.assertTrue(map.removeValues(1, "b", "c"));
        Assertions.assertEquals(Collections.singletonList("a"), map.get(1));
    }

    @Test
    public void testFilterAllValues() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertTrue(map.putValues(1, "a", "b", "c"));
        Assertions.assertTrue(map.putValues(2, "a", "b", "c"));

        Assertions.assertEquals(map, map.filterAllValues((k, v) -> StringKit.equals(v, "a")));
        Assertions.assertEquals(Collections.singletonList("a"), map.getValues(1));
        Assertions.assertEquals(Collections.singletonList("a"), map.getValues(2));

        Assertions.assertEquals(map, map.filterAllValues(v -> !StringKit.equals(v, "a")));
        Assertions.assertEquals(Collections.emptyList(), map.getValues(1));
        Assertions.assertEquals(Collections.emptyList(), map.getValues(2));
    }

    @Test
    public void testReplaceAllValues() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        Assertions.assertTrue(map.putValues(1, "a", "b", "c"));
        Assertions.assertTrue(map.putValues(2, "a", "b", "c"));

        Assertions.assertEquals(map, map.replaceAllValues((k, v) -> v + "2"));
        Assertions.assertEquals(Arrays.asList("a2", "b2", "c2"), map.getValues(1));
        Assertions.assertEquals(Arrays.asList("a2", "b2", "c2"), map.getValues(2));

        Assertions.assertEquals(map, map.replaceAllValues(v -> v + "3"));
        Assertions.assertEquals(Arrays.asList("a23", "b23", "c23"), map.getValues(1));
        Assertions.assertEquals(Arrays.asList("a23", "b23", "c23"), map.getValues(2));
    }

    @Test
    public void getValuesTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        Assertions.assertEquals(Collections.emptyList(), map.getValues(2));
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), map.getValues(1));
    }

    @Test
    public void sizeTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        Assertions.assertEquals(0, map.size(2));
        Assertions.assertEquals(3, map.size(1));
    }

    @Test
    public void allForEachTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putValues(1, "a", "b", "c");
        List<Integer> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        map.allForEach((k, v) -> {
            keys.add(k);
            values.add(v);
        });
        Assertions.assertEquals(Arrays.asList(1, 1, 1), keys);
        Assertions.assertEquals(Arrays.asList("a", "b", "c"), values);
    }

    @Test
    public void allValuesTest() {
        MultiValueMap<Integer, String> map = new ListValueMap<>();
        map.putAllValues(1, Arrays.asList("a", "b", "c"));
        map.putAllValues(2, Arrays.asList("d", "e"));
        Assertions.assertEquals(
                Arrays.asList("a", "b", "c", "d", "e"),
                map.allValues()
        );
    }

}
