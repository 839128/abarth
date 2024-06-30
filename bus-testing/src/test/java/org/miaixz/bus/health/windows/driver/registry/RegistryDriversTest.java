package org.miaixz.bus.health.windows.driver.registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnabledOnOs(OS.WINDOWS)
class RegistryDriversTest {

    @Test
    void testProcessPerformanceData() {
        Map<Integer, ProcessPerformanceData.PerfCounterBlock> processMap = ProcessPerformanceData
                .buildProcessMapFromRegistry(null);
        assertNotNull(processMap);
        assertThat("Process map should not be empty", processMap, is(not(anEmptyMap())));
    }

    @Test
    void testThreadPerformanceData() {
        Map<Integer, ThreadPerformanceData.PerfCounterBlock> threadMap = ThreadPerformanceData
                .buildThreadMapFromRegistry(null);
        assertNotNull(threadMap);
        assertThat("Thread map should not be empty", threadMap, is(not(anEmptyMap())));
    }

    @Test
    void testSessionWtsData() {
        assertThat("Sessions list from registry should not be empty", HkeyUserData.queryUserSessions(),
                is(not(empty())));
        assertDoesNotThrow(SessionWtsData::queryUserSessions);
        assertDoesNotThrow(NetSessionData::queryUserSessions);
    }

    @Test
    void testProcessWtsData() {
        assertThat("Process WTS map should not be empty", ProcessWtsData.queryProcessWtsMap(null),
                is(not(anEmptyMap())));
    }

}
