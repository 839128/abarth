package org.miaixz.bus.health.windows.driver;

import com.sun.jna.platform.win32.Guid.GUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Tuple;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs(OS.WINDOWS)
class DeviceTreeTest {
    private static final GUID GUID_DEVINTERFACE_NET = new GUID("{CAC88484-7515-4C03-82E6-71A87ABAC361}");

    @Test
    void testQueryDeviceTree() {
        Tuple tree = DeviceTree.queryDeviceTree(GUID_DEVINTERFACE_NET);
        Set<Integer> rootSet = tree.get(0);
        assertThat("Tree root set must not be empty", rootSet, is(not(empty())));
        Map<Integer, Integer> parentMap = tree.get(1);
        Set<Integer> branchSet = parentMap.keySet().stream().collect(Collectors.toSet());
        branchSet.retainAll(rootSet); // intersection
        assertThat("Branches cannot match root", branchSet, is(empty()));
        Set<Integer> nodeSet = parentMap.keySet().stream().collect(Collectors.toSet());
        nodeSet.addAll(rootSet); // union

        assertTrue(nodeSet.containsAll(tree.get(2)), "Name map should only have nodes as keys");
        assertTrue(nodeSet.containsAll(tree.get(3)), "Device Id should only have nodes as keys");
        assertTrue(nodeSet.containsAll(tree.get(4)), "Manufacturer map should only have nodes as keys");
    }

}
