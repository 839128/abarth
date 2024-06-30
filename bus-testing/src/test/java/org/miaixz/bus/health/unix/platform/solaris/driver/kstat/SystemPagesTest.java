package org.miaixz.bus.health.unix.platform.solaris.driver.kstat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Pair;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@EnabledOnOs(OS.SOLARIS)
class SystemPagesTest {

    @Test
    void testQueryAvailableTotal() {
        Pair<Long, Long> availTotal = SystemPages.queryAvailableTotal();
        assertThat("Total should be a positive number", availTotal.getLeft().longValue(), greaterThan(0L));
        assertThat("Available should not exceed total", availTotal.getRight().longValue(),
                lessThanOrEqualTo(availTotal.getRight().longValue()));
    }

}
