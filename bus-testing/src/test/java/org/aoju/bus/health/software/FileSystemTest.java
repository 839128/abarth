package org.aoju.bus.health.software;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.Platform;
import org.aoju.bus.health.builtin.software.FileSystem;
import org.aoju.bus.health.builtin.software.OSFileStore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test File System
 */
public class FileSystemTest {

    /**
     * Test file system.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testFileSystem() throws IOException {
        FileSystem filesystem = Builder.getOs().getFileSystem();
        assertTrue(filesystem.getOpenFileDescriptors() >= 0L);
        assertTrue(filesystem.getMaxFileDescriptors() >= 0L);
        List<OSFileStore> fs = filesystem.getFileStores();
        for (OSFileStore store : fs) {
            assertNotNull(store.getName());
            assertNotNull(store.getVolume());
            assertNotNull(store.getLabel());
            assertNotNull(store.getLogicalVolume());
            assertNotNull(store.getDescription());
            assertNotNull(store.getType());
            assertFalse(store.getOptions().isEmpty());
            assertNotNull(store.getMount());
            assertNotNull(store.getUUID());
            assertTrue(store.getTotalSpace() >= 0);
            assertTrue(store.getUsableSpace() >= 0);
            assertTrue(store.getFreeSpace() >= 0);
            if (Platform.getCurrentOs() != Platform.OS.WINDOWS) {
                assertTrue(store.getFreeInodes() >= 0);
                assertTrue(store.getTotalInodes() >= store.getFreeInodes());
            }
            if (!store.getDescription().equals("Network drive")) {
                assertTrue(store.getUsableSpace() <= store.getTotalSpace());
            }


            assertEquals("name", store.getName());
            assertEquals("volume", store.getVolume());
            assertEquals("label", store.getLabel());
            assertEquals("logical volume", store.getLogicalVolume());
            assertEquals("desc", store.getDescription());
            assertEquals("type", store.getType());
            assertEquals("mount", store.getMount());
            assertEquals("uuid", store.getUUID());
            assertEquals(12345L, store.getTotalSpace());
            assertEquals(2345L, store.getFreeSpace());
            assertEquals(1234L, store.getUsableSpace());
        }

        List<OSFileStore> localFs = filesystem.getFileStores(true);
        assertTrue(localFs.size() <= fs.size());
    }

}
