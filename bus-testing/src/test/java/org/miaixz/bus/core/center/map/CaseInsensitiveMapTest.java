package org.miaixz.bus.core.center.map;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.MapKit;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaseInsensitiveMapTest {

    @Test
    public void caseInsensitiveMapTest() {
        final CaseInsensitiveMap<String, String> map = new CaseInsensitiveMap<>();
        map.put("aAA", "OK");
        assertEquals("OK", map.get("aaa"));
        assertEquals("OK", map.get("AAA"));
    }

    @Test
    public void caseInsensitiveLinkedMapTest() {
        final CaseInsensitiveLinkedMap<String, String> map = new CaseInsensitiveLinkedMap<>();
        map.put("aAA", "OK");
        assertEquals("OK", map.get("aaa"));
        assertEquals("OK", map.get("AAA"));
    }

    @Test
    public void mergeTest() {
        final Map.Entry<String, String> b = MapKit.entry("a", "value");
        final Map.Entry<String, String> a = MapKit.entry("A", "value");
        final CaseInsensitiveMap<Object, Object> map = new CaseInsensitiveMap<>();
        map.merge(b.getKey(), b.getValue(), (A, B) -> A);
        map.merge(a.getKey(), a.getValue(), (A, B) -> A);

        assertEquals(1, map.size());
    }

    @Test
    public void issueTest() {
        final Map<String, Object> map = new CaseInsensitiveLinkedMap<>();
        map.put("b", 2);
        map.put("a", 1);

        final AtomicInteger index = new AtomicInteger();
        map.forEach((k, v) -> {
            if (0 == index.get()) {
                assertEquals("b", k);
            } else if (1 == index.get()) {
                assertEquals("a", k);
            }

            index.getAndIncrement();
        });
    }

    @Test
    public void issueTest2() {
        final Map<String, Object> map = new CaseInsensitiveTreeMap<>();
        map.put("b", 2);
        map.put("a", 1);

        final AtomicInteger index = new AtomicInteger();
        map.forEach((k, v) -> {
            if (0 == index.get()) {
                assertEquals("a", k);
            } else if (1 == index.get()) {
                assertEquals("b", k);
            }

            index.getAndIncrement();
        });
    }

}
