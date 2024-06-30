package org.miaixz.bus.health.unix.platform.openbsd.driver.disk;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.tuple.Tuple;
import org.miaixz.bus.health.builtin.hardware.HWPartition;
import org.miaixz.bus.health.unix.platform.openbsd.OpenBsdSysctlKit;
import org.miaixz.bus.health.unix.platform.openbsd.software.OpenBsdOperatingSystem;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DisklabelTest {

    @Test
    void testDisklabel() {
        String[] devices = OpenBsdSysctlKit.sysctl("hw.disknames", "").split(",");
        for (String device : devices) {
            String diskName = device.split(":")[0];
            Tuple diskdata = Disklabel.getDiskParams(diskName);
            // First 3 only available with elevation
            if (new OpenBsdOperatingSystem().isElevated()) {
                assertThat("Disk label is not null", diskdata.get(0), not(nullValue()));
                assertThat("Disk duid is not null", diskdata.get(1), not(nullValue()));
                assertThat("Disk size is nonnegative", diskdata.get(2), greaterThanOrEqualTo(0L));
                for (HWPartition part : (List<HWPartition>) diskdata.get(3)) {
                    assertTrue(part.getIdentification().startsWith(diskName), "Partition ID starts with disk");
                }
            }
        }
    }

}
