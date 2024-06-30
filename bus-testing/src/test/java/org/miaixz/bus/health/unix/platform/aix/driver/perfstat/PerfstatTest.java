package org.miaixz.bus.health.unix.platform.aix.driver.perfstat;

import com.sun.jna.Native;
import com.sun.jna.platform.unix.aix.Perfstat.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledOnOs(OS.AIX)
class PerfstatTest {

    @Test
    void testQueryConfig() {
        perfstat_partition_config_t config = PerfstatConfig.queryConfig();
        assertThat("Should have at least one logical CPU", config.lcpus, greaterThan(0));
    }

    @Test
    void testQueryCpu() {
        perfstat_cpu_t[] cpus = PerfstatCpu.queryCpu();
        assertThat("Should have at least one CPU", cpus.length, greaterThan(0));
        for (perfstat_cpu_t cpu : cpus) {
            assertThat("Should have at least one idle tick", cpu.idle, greaterThan(0L));
        }

        perfstat_cpu_total_t total = PerfstatCpu.queryCpuTotal();
        assertThat("Should have at least one idle tick", total.idle, greaterThan(0L));

        long affinity = PerfstatCpu.queryCpuAffinityMask();
        assertThat("Should have at least one CPU", affinity, greaterThan(0L));
    }

    @Test
    void testQueryDiskStats() {
        perfstat_disk_t[] disks = PerfstatDisk.queryDiskStats();
        assertThat("Should have at least one disk", disks.length, greaterThan(0));
        for (perfstat_disk_t disk : disks) {
            // Virtual disks may give 0 capacity but also have 0 time
            if (disk.time == 0) {
                assertThat("Should have a nonnegative disk capacity", disk.size, greaterThanOrEqualTo(0L));
            } else {
                assertThat("Should have a nonzero disk capacity", disk.size, greaterThan(0L));
            }
        }
    }

    @Test
    void testQueryMemory() {
        perfstat_memory_total_t mem = PerfstatMemory.queryMemoryTotal();
        assertThat("Should have nonzero memory", mem.real_total, greaterThan(0L));
    }

    @Test
    void testQueryNetInterface() {
        perfstat_netinterface_t[] nets = PerfstatNetInterface.queryNetInterfaces();
        assertThat("Should have at least one interface", nets.length, greaterThan(0));
        for (perfstat_netinterface_t net : nets) {
            assertThat("Should have a nonempty name", Native.toString(net.name), not(emptyString()));
        }
    }

    @Test
    void testQueryProcesses() {
        perfstat_process_t[] procs = PerfstatProcess.queryProcesses();
        assertThat("Should have at least one process", procs.length, greaterThan(0));
        for (perfstat_process_t proc : procs) {
            assertThat("Should have at least one thread", proc.num_threads, greaterThan(0L));
        }
    }

    @Test
    void testQueryProtocol() {
        perfstat_protocol_t[] protos = PerfstatProtocol.queryProtocols();
        assertThat("Should have at least one protocol", protos.length, greaterThan(0));
        List<String> validProtos = Arrays.asList("ip", "ipv6", "icmp", "icmpv6", "udp", "tcp", "rpc", "nfs", "nfsv2",
                "nfsv3", "nfsv4");
        for (perfstat_protocol_t proto : protos) {
            String protoName = Native.toString(proto.name);
            assertTrue(validProtos.contains(protoName), "Protocol must be in defined list of names");
        }
    }

}
