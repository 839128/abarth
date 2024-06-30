package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@EnabledOnOs(OS.LINUX)
class DevicetreeTest {

    @Test
    void testQueryModel() {
        assertDoesNotThrow(Devicetree::queryModel);
    }
}
