package org.miaixz.bus.health.windows.driver.perfmon;

import com.sun.jna.platform.win32.VersionHelpers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.health.windows.PerfCounterQuery;
import org.miaixz.bus.health.windows.PerfCounterWildcardQuery;
import org.miaixz.bus.health.windows.driver.perfmon.MemoryInformation.PageSwapProperty;
import org.miaixz.bus.health.windows.driver.perfmon.PagingFile.PagingPercentProperty;
import org.miaixz.bus.health.windows.driver.perfmon.PhysicalDisk.PhysicalDiskProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessInformation.HandleCountProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessInformation.IdleProcessorTimeProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessInformation.ProcessPerformanceProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessorInformation.InterruptsProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessorInformation.ProcessorFrequencyProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessorInformation.ProcessorTickCountProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ProcessorInformation.ProcessorUtilityTickCountProperty;
import org.miaixz.bus.health.windows.driver.perfmon.SystemInformation.ContextSwitchProperty;
import org.miaixz.bus.health.windows.driver.perfmon.ThreadInformation.ThreadPerformanceProperty;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.is;
import static org.miaixz.bus.health.windows.driver.perfmon.PerfmonConsts.*;

@EnabledOnOs(OS.WINDOWS)
class PerfmonDriversTest {

    @Test
    void testQueryPageSwaps() {
        assertThat("Failed PDH queryPageSwaps", PerfCounterQuery.queryValuesFromPDH(PageSwapProperty.class, MEMORY),
                is(aMapWithSize(PageSwapProperty.values().length)));
        assertThat("Failed WMI queryPageSwaps",
                PerfCounterQuery.queryValuesFromWMI(PageSwapProperty.class, WIN32_PERF_RAW_DATA_PERF_OS_MEMORY),
                is(aMapWithSize(PageSwapProperty.values().length)));
    }

    @Test
    void testQuerySwapUsed() {
        assertThat("Failed PDH querySwapUsed",
                PerfCounterQuery.queryValuesFromPDH(PagingPercentProperty.class, PAGING_FILE),
                is(aMapWithSize(PagingPercentProperty.values().length)));
        assertThat("Failed WMI querySwapUsed",
                PerfCounterQuery.queryValuesFromWMI(PagingPercentProperty.class,
                        WIN32_PERF_RAW_DATA_PERF_OS_PAGING_FILE),
                is(aMapWithSize(PagingPercentProperty.values().length)));
    }

    @Test
    void testQueryDiskCounters() {
        testWildcardCounters("PDH DiskCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(PhysicalDiskProperty.class, PHYSICAL_DISK));
        testWildcardCounters("WMI DiskCounters", PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(
                PhysicalDiskProperty.class, WIN32_PERF_RAW_DATA_PERF_DISK_PHYSICAL_DISK_WHERE_NAME_NOT_TOTAL));
    }

    @Test
    void testQueryProcessCounters() {
        testWildcardCounters("PDH ProcessCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(ProcessPerformanceProperty.class, PROCESS));
        testWildcardCounters("WMI ProcessCounters", PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(
                ProcessPerformanceProperty.class, WIN32_PERFPROC_PROCESS_WHERE_NOT_NAME_LIKE_TOTAL));
    }

    @Test
    void testQueryHandles() {
        testWildcardCounters("PDH Handles",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(HandleCountProperty.class, PROCESS));
        testWildcardCounters("WMI Handles", PerfCounterWildcardQuery
                .queryInstancesAndValuesFromWMI(HandleCountProperty.class, WIN32_PERFPROC_PROCESS));
    }

    @Test
    void testQueryIdleProcessCounters() {
        testWildcardCounters("PDH IdleProcessCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(IdleProcessorTimeProperty.class, PROCESS));
        testWildcardCounters("WMI IdleProcessCounters", PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(
                IdleProcessorTimeProperty.class, WIN32_PERFPROC_PROCESS_WHERE_IDPROCESS_0));
    }

    @Test
    void testQueryProcessorCounters() {
        testWildcardCounters("PDH ProcessorCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(ProcessorTickCountProperty.class, PROCESSOR));
        testWildcardCounters("WMI ProcessorCounters", PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(
                ProcessorTickCountProperty.class, WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NAME_NOT_TOTAL));
        if (VersionHelpers.IsWindows7OrGreater()) {
            testWildcardCounters("PDH ProcessorCounters", PerfCounterWildcardQuery
                    .queryInstancesAndValuesFromPDH(ProcessorTickCountProperty.class, PROCESSOR_INFORMATION));
            testWildcardCounters("WMI ProcessorCounters",
                    PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(ProcessorTickCountProperty.class,
                            WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL));

            if (VersionHelpers.IsWindows8OrGreater()) {
                testWildcardCounters("PDH ProcessorUtilityCounters",
                        PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(ProcessorUtilityTickCountProperty.class,
                                PROCESSOR_INFORMATION));
                testWildcardCounters("WMI ProcessorUtilityCounters",
                        PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(ProcessorUtilityTickCountProperty.class,
                                WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL));
            }
        }
    }

    @Test
    void testQueryInterruptCounters() {
        assertThat("Failed PDH queryInterruptCounters",
                PerfCounterQuery.queryValuesFromPDH(InterruptsProperty.class, PROCESSOR),
                is(aMapWithSize(InterruptsProperty.values().length)));
        assertThat("Failed WMI queryInterruptCounters",
                PerfCounterQuery.queryValuesFromWMI(InterruptsProperty.class,
                        WIN32_PERF_RAW_DATA_PERF_OS_PROCESSOR_WHERE_NAME_TOTAL),
                is(aMapWithSize(InterruptsProperty.values().length)));
    }

    @Test
    void testQueryFrequencyCounters() {
        testWildcardCounters("PDH FrequencyCounters", PerfCounterWildcardQuery
                .queryInstancesAndValuesFromPDH(ProcessorFrequencyProperty.class, PROCESSOR_INFORMATION));
        testWildcardCounters("WMI FrequencyCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(ProcessorFrequencyProperty.class,
                        WIN32_PERF_RAW_DATA_COUNTERS_PROCESSOR_INFORMATION_WHERE_NOT_NAME_LIKE_TOTAL));
    }

    @Test
    void testQueryContextSwitchCounters() {
        assertThat("Failed PDH queryInterruptCounters",
                PerfCounterQuery.queryValuesFromPDH(ContextSwitchProperty.class, SYSTEM),
                is(aMapWithSize(ContextSwitchProperty.values().length)));
        assertThat("Failed WMI queryInterruptCounters",
                PerfCounterQuery.queryValuesFromWMI(ContextSwitchProperty.class, WIN32_PERF_RAW_DATA_PERF_OS_SYSTEM),
                is(aMapWithSize(ContextSwitchProperty.values().length)));
    }

    @Test
    void testQueryThreadCounters() {
        testWildcardCounters("PDH ThreadCounters",
                PerfCounterWildcardQuery.queryInstancesAndValuesFromPDH(ThreadPerformanceProperty.class, THREAD));
        testWildcardCounters("WMI ThreadCounters", PerfCounterWildcardQuery.queryInstancesAndValuesFromWMI(
                ThreadPerformanceProperty.class, WIN32_PERF_RAW_DATA_PERF_PROC_THREAD_WHERE_NOT_NAME_LIKE_TOTAL));
    }

    private <T extends PerfCounterWildcardQuery.PdhCounterWildcardProperty> void testWildcardCounters(String s,
                                                                                                      Pair<List<String>, Map<T, List<Long>>> counterData) {
        int instanceCount = counterData.getLeft().size();
        Map<T, List<Long>> counters = counterData.getRight();
        for (List<Long> counter : counters.values()) {
            assertThat("Failed " + s + " instance count", counter.size(), is(instanceCount));
        }

        int expectedSize = ((Class<T>) counters.keySet().iterator().next().getClass()).getEnumConstants().length - 1;
        assertThat("Failed " + s + " map size", counters, is(aMapWithSize(expectedSize)));
    }

}
