package org.miaixz.bus.health.windows.driver.wmi;

import com.sun.jna.platform.win32.COM.WbemcliUtil;
import com.sun.jna.platform.win32.VersionHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.health.windows.WmiKit;
import org.miaixz.bus.health.windows.WmiQueryHandler;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@EnabledOnOs(OS.WINDOWS)
class WMIDriversTest {

    @Test
    void testQueryWMIMSAcpi() {
        assertThat("Failed MSAcpiThermalZoneTemperature.queryCurrentTemperature",
                MSAcpiThermalZoneTemperature.queryCurrentTemperature().getResultCount(), is(greaterThanOrEqualTo(0)));
    }

    @Test
    void testQueryWMIMSFT() {
        WmiQueryHandler handler = WmiQueryHandler.createInstance();
        assertThat(handler, is(notNullValue()));
        boolean comInit = handler.initCOM();

        assertThat("Failed MSFTStorage.queryPhysicalDisks", MSFTStorage.queryPhysicalDisks(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));
        assertThat("Failed MSFTStorage.queryStoragePoolPhysicalDisks",
                MSFTStorage.queryStoragePoolPhysicalDisks(handler).getResultCount(), is(greaterThanOrEqualTo(0)));
        assertThat("Failed MSFTStorage.queryStoragePools", MSFTStorage.queryStoragePools(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));
        assertThat("Failed MSFTStorage.queryVirtualDisks", MSFTStorage.queryVirtualDisks(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));

        if (comInit) {
            handler.unInitCOM();
        }
    }

    // OhmHardware and OhmSensor excluded
    // We'll never have OHM running during testing

    @Test
    void testQueryWMIWin32() {
        WmiQueryHandler handler = WmiQueryHandler.createInstance();
        assertThat(handler, is(notNullValue()));
        boolean comInit = handler.initCOM();

        assertThat("Failed Win32DiskDrive.queryDiskDrive", Win32DiskDrive.queryDiskDrive(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));

        assertThat("Failed Win32DiskDriveToDiskPartition.queryDriveToPartition",
                Win32DiskDriveToDiskPartition.queryDriveToPartition(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));

        assertThat("Failed Win32DiskPartition.queryPartition",
                Win32DiskPartition.queryPartition(handler).getResultCount(), is(greaterThanOrEqualTo(0)));

        assertThat("Failed Win32LogicalDiskToPartition.queryDiskToPartition",
                Win32LogicalDiskToPartition.queryDiskToPartition(handler).getResultCount(),
                is(greaterThanOrEqualTo(0)));

        if (comInit) {
            handler.unInitCOM();
        }

        assertThat("Failed Win32BaseBoard.queryBaseboardInfo", Win32BaseBoard.queryBaseboardInfo().getResultCount(),
                is(greaterThan(0)));

        assertThat("Failed Win32Bios.queryBiosInfo", Win32Bios.queryBiosInfo().getResultCount(), is(greaterThan(0)));
        assertThat("Failed Win32Bios.querySerialNumber", Win32Bios.querySerialNumber().getResultCount(),
                is(greaterThan(0)));

        assertThat("Failed Win32ComputerSystemProduct.queryIdentifyingNumberUUID",
                Win32ComputerSystemProduct.queryIdentifyingNumberUUID().getResultCount(), is(greaterThan(0)));

        assertThat("Failed Win32Fan.querySpeed", Win32Fan.querySpeed().getResultCount(), is(greaterThanOrEqualTo(0)));

        WbemcliUtil.WmiResult<Win32LogicalDisk.LogicalDiskProperty> ld = Win32LogicalDisk.queryLogicalDisk(null, false);
        assertThat("Failed Win32LogicalDisk.queryLogicalDisk", ld.getResultCount(), is(greaterThan(0)));
        assertThat(
                "Failed Win32LogicalDisk.queryLogicalDisk", Win32LogicalDisk
                        .queryLogicalDisk(WmiKit.getString(ld, Win32LogicalDisk.LogicalDiskProperty.NAME, 0), false).getResultCount(),
                is(greaterThan(0)));
        assertThat("Failed Win32LogicalDisk.queryLogicalDisk",
                Win32LogicalDisk.queryLogicalDisk(null, true).getResultCount(), is(greaterThan(0)));

        assertThat("Failed Win32OperatingSystem.queryOsVersion", Win32OperatingSystem.queryOsVersion().getResultCount(),
                is(greaterThan(0)));

        if (VersionHelpers.IsWindows10OrGreater()) {
            assertThat("Failed Win32PhysicalMemory.queryphysicalMemory",
                    Win32PhysicalMemory.queryphysicalMemory().getResultCount(), is(greaterThan(0)));
        } else {
            assertThat("Failed Win32PhysicalMemory.queryphysicalMemoryWin8",
                    Win32PhysicalMemory.queryphysicalMemoryWin8().getResultCount(), is(greaterThan(0)));
        }

        WbemcliUtil.WmiResult<Win32Process.CommandLineProperty> cl = Win32Process.queryCommandLines(null);
        assertThat("Failed Win32Process.queryCommandLines", cl.getResultCount(), is(greaterThan(0)));
        Set<Integer> clset = IntStream.range(0, cl.getResultCount())
                .map(i -> WmiKit.getUint32(cl, Win32Process.CommandLineProperty.PROCESSID, i)).boxed().collect(Collectors.toSet());
        assertThat("Failed Win32Process.queryProcesses", Win32Process.queryProcesses(clset).getResultCount(),
                is(both(greaterThan(0)).and(lessThanOrEqualTo(clset.size()))));

        Win32ProcessCached cache = Win32ProcessCached.getInstance();
        assertThat("Failed Win32ProcessCached.getCommandLine",
                cache.getCommandLine(WmiKit.getUint32(cl, Win32Process.CommandLineProperty.PROCESSID, 0), 0L), is(notNullValue()));

        assertThat("Failed Win32Processor.queryBitness", Win32Processor.queryBitness().getResultCount(),
                is(greaterThan(0)));
        assertThat("Failed Win32Processor.queryProcessorId", Win32Processor.queryProcessorId().getResultCount(),
                is(greaterThan(0)));
        assertThat("Failed Win32Processor.queryVoltage", Win32Processor.queryVoltage().getResultCount(),
                is(greaterThan(0)));

        assertThat("Failed Win32VideoController.queryVideoController",
                Win32VideoController.queryVideoController().getResultCount(), is(greaterThanOrEqualTo(0)));
    }

}
