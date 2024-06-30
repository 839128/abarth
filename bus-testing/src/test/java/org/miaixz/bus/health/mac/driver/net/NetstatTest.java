package org.miaixz.bus.health.mac.driver.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnabledOnOs(OS.MAC)
class NetstatTest {

    @Test
    void testQueryIfData() {
        assertThat("IfData map should not be empty.", NetStat.queryIFdata(-1), is(not(anEmptyMap())));
    }

}
