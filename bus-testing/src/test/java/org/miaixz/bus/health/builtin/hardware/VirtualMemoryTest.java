package org.miaixz.bus.health.builtin.hardware;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test GlobalMemory
 */
class VirtualMemoryTest {

    /**
     * Test VirtualMemory.
     */
    @Test
    void testGlobalMemory() {
        Platform si = new Platform();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        VirtualMemory vm = memory.getVirtualMemory();
        assertThat("VM object shouldn't be null", vm, is(notNullValue()));

        // Swap tests
        assertThat("VM's swap pages in shouldn't be negative", vm.getSwapPagesIn(), is(greaterThanOrEqualTo(0L)));
        assertThat("VM's swap pages out shouldn't be negative", vm.getSwapPagesOut(), is(greaterThanOrEqualTo(0L)));
        assertThat("VM's swap total shouldn't be negative", vm.getSwapTotal(), is(greaterThanOrEqualTo(0L)));
        assertThat("VM's swap used shouldn't be negative", vm.getSwapUsed(), is(greaterThanOrEqualTo(0L)));
        assertThat("VM's swap used should be less than or equal to VM swap total", vm.getSwapUsed(),
                is(lessThanOrEqualTo(vm.getSwapTotal())));
        assertThat("VM's max should be greater than or qual to VM swap total", vm.getVirtualMax() >= vm.getSwapTotal(),
                is(true));
        assertThat("VM's virtual in use shouldn't be negative", vm.getVirtualInUse(), is(greaterThanOrEqualTo(0L)));
        assertThat("VM's toString contains 'Used'", vm.toString(), containsString("Used"));
    }

}
