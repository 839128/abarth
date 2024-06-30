package org.miaixz.bus.health.unix.platform.aix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.core.lang.tuple.Triplet;
import org.miaixz.bus.health.builtin.hardware.HWPartition;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@EnabledOnOs(OS.AIX)
class LsDevicesTest {

    @Test
    void testQueryLsDevices() {
        Map<String, Pair<Integer, Integer>> majMinMap = Ls.queryDeviceMajorMinor();
        assertThat("Device Major Minor Map shouldn't be empty", majMinMap, not(anEmptyMap()));
        // Key set is device name
        boolean foundNonNull = false;
        boolean foundPartitions = false;
        for (String device : majMinMap.keySet()) {
            Pair<String, String> modSer = Lscfg.queryModelSerial(device);
            if (modSer.getLeft() != null || modSer.getRight() != null) {
                foundNonNull = true;
                List<HWPartition> lvs = Lspv.queryLogicalVolumes(device, majMinMap);
                if (!lvs.isEmpty()) {
                    foundPartitions = true;
                }
            }
            if (foundNonNull && foundPartitions) {
                break;
            }
        }
        assertTrue(foundNonNull, "At least one device model/serial should be non null");
        assertTrue(foundPartitions, "At least one device should have partitions");

        List<String> lscfg = Lscfg.queryAllDevices();
        assertThat("Output of lscfg should be nonempty", lscfg.size(), greaterThan(0));

        Triplet<String, String, String> modSerVer = Lscfg.queryBackplaneModelSerialVersion(lscfg);
        // Either all should be null or none should be null
        if (!(modSerVer.getLeft() == null && modSerVer.getMiddle() == null && modSerVer.getRight() == null)) {
            assertNotNull(modSerVer.getLeft(), "Backplane Model should not be null");
            assertNotNull(modSerVer.getMiddle(), "Backplane Serial should not be null");
            assertNotNull(modSerVer.getRight(), "Backplane Version should not be null");
        }
    }

}
