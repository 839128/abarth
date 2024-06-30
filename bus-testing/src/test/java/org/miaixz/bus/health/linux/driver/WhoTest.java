package org.miaixz.bus.health.linux.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.builtin.software.OSSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnabledOnOs(OS.LINUX)
class WhoTest {

    @Test
    void testQueryUtxent() {
        for (OSSession session : Who.queryUtxent()) {
            assertThat("Session login time should be greater than 0", session.getLoginTime(), is(greaterThan(0L)));
            assertThat("Session login time should be less than current time", session.getLoginTime(),
                    is(lessThan(System.currentTimeMillis())));
            assertThat("User should be non-empty", session.getUserName(), is(not(emptyOrNullString())));
            assertThat("Devices should be non-empty", session.getTerminalDevice(), is(not(emptyOrNullString())));
        }
    }

}
