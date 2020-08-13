package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.HWDiskStore;
import org.aoju.bus.health.builtin.hardware.HWPartition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Disks
 */
public class DisksTest {

    /**
     * Test disks extraction.
     */
    @Test
    public void testDisks() {

        for (HWDiskStore disk : Builder.getHardware().getDiskStores()) {
            assertEquals(disk, disk);
            assertNotEquals(disk, null);
            assertNotEquals(disk, "A String");
            List<HWPartition> parts = disk.getPartitions();
            List<HWPartition> partList = new ArrayList<>(parts.size());
            for (HWPartition part : parts) {
                partList.add(new HWPartition(part.getIdentification(), part.getName(), part.getType(), part.getUuid(),
                        part.getSize(), part.getMajor(), part.getMinor(), part.getMountPoint()));
            }

            assertNotNull(disk.getName());
            assertNotNull(disk.getModel());
            assertNotNull(disk.getSerial());
            assertTrue(disk.getSize() >= 0);
            assertTrue(disk.getReads() >= 0);
            assertTrue(disk.getReadBytes() >= 0);
            assertTrue(disk.getWrites() >= 0);
            assertTrue(disk.getWriteBytes() >= 0);
            assertTrue(disk.getTransferTime() >= 0);
            assertTrue(disk.getTimeStamp() >= 0);
            assertTrue(disk.toString().contains(disk.getName()));

            long oldReads = disk.getReads();
            long oldReadBytes = disk.getReadBytes();
            assertTrue(disk.updateAttributes());
            assertTrue(disk.getReads() >= oldReads);
            assertTrue(disk.getReadBytes() >= oldReadBytes);

            for (HWPartition partition : disk.getPartitions()) {
                assertNotNull(partition.getIdentification());
                assertNotNull(partition.getName());
                assertNotNull(partition.getType());
                assertNotNull(partition.getUuid());
                assertNotNull(partition.getMountPoint());
                assertTrue(partition.getSize() >= 0);
                assertTrue(partition.getMajor() >= 0);
                assertTrue(partition.getMinor() >= 0);
                assertTrue(partition.toString().contains(partition.getIdentification()));
            }
        }
    }

}
