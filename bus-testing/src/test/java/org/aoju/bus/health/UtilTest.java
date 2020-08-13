package org.aoju.bus.health;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test general utility methods
 */
public class UtilTest {

    @Test
    public void testSleep() {
        // Windows counters may be up to 10ms off
        long now = System.nanoTime();
        Util.sleep(100);
        assertTrue(System.nanoTime() - now >= 90_000_000);
    }

    @Test
    public void testWildcardMatch() {
        assertFalse(Util.wildcardMatch("Test", "est"));
        assertTrue(Util.wildcardMatch("Test", "^est"));
        assertFalse(Util.wildcardMatch("Test", "^^est"));
        assertTrue(Util.wildcardMatch("Test", "?est"));
        assertFalse(Util.wildcardMatch("Test", "^?est"));
        assertTrue(Util.wildcardMatch("Test", "*est"));
        assertFalse(Util.wildcardMatch("Test", "^*est"));

        assertFalse(Util.wildcardMatch("Test", "T?t"));
        assertTrue(Util.wildcardMatch("Test", "T??t"));
        assertTrue(Util.wildcardMatch("Test", "T*t"));

        assertFalse(Util.wildcardMatch("Test", "Tes"));
        assertTrue(Util.wildcardMatch("Test", "Tes?"));
        assertTrue(Util.wildcardMatch("Test", "Tes*"));

        assertFalse(Util.wildcardMatch("Test", "Te?"));
        assertTrue(Util.wildcardMatch("Test", "Te*"));
    }

    @Test
    public void testIsBlank() {
        assertTrue(Util.isBlank(""));
        assertTrue(Util.isBlank(null));
        assertFalse(Util.isBlank("Not blank"));
    }
}
