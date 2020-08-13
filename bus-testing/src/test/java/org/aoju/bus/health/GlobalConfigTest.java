package org.aoju.bus.health;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;
import static oshi.util.GlobalConfig.*;

public class GlobalConfigTest {

    @Before
    public void setUp() {
        clear();
    }

    @Test
    public void testGetString() {
        assertNull(get("oshi.test.property", null));
        set("oshi.test.property", "test");
        assertEquals("test", get("oshi.test.property", null));
        set("oshi.test.property", 123);
        assertEquals("123", get("oshi.test.property", null));
    }

    @Test
    public void testGetInteger() {
        assertEquals(0, get("oshi.test.property", 0));
        set("oshi.test.property", 123);
        assertEquals(123, get("oshi.test.property", 0));
        assertEquals("123", get("oshi.test.property", null));

        // Invalid integer
        set("oshi.test.property", "1.23");
        assertEquals(0, get("oshi.test.property", 0));
    }

    @Test
    public void testGetDouble() {
        assertTrue(get("oshi.test.property", 0.0) == 0.0);
        set("oshi.test.property", 1.23d);
        assertTrue(get("oshi.test.property", 0.0) == 1.23);
        assertEquals("1.23", get("oshi.test.property", null));

        // Invalid double
        set("oshi.test.property", "1.2.3");
        assertTrue(get("oshi.test.property", 0.0) == 0.0);
    }

    @Test
    public void testGetBoolean() {
        assertFalse(get("oshi.test.property", false));
        set("oshi.test.property", true);
        assertTrue(get("oshi.test.property", false));
        assertEquals("true", get("oshi.test.property", null));
    }

    @Test
    public void testSetNull() {
        set("oshi.test.property", "test");
        set("oshi.test.property", null);
        assertEquals("123", get("oshi.test.property", "123"));
    }

    @Test
    public void testRemove() {
        set("oshi.test.property", "test");
        remove("oshi.test.property");
        assertEquals("123", get("oshi.test.property", "123"));
    }

    @Test
    public void testLoad() {
        Properties updates = new Properties();
        updates.setProperty("oshi.test.property", "321");

        load(updates);
        assertEquals("321", get("oshi.test.property", null));
    }

    @Test
    public void testPropertyExceptionMessage() {
        set("oshi.test.property", "test");
        assertEquals("Invalid property: \"oshi.test.property\" = test",
                new GlobalConfig.PropertyException("oshi.test.property").getMessage());
    }
}
