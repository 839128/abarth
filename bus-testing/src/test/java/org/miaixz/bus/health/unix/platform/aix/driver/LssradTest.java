package org.miaixz.bus.health.unix.platform.aix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.lang.tuple.Pair;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.not;


@EnabledOnOs(OS.AIX)
class LssradTest {

    @Test
    void testQueryLssrad() {
        Map<Integer, Pair<Integer, Integer>> nodeMap = Lssrad.queryNodesPackages();
        assertThat("Node Map shouldn't be empty", nodeMap, not(anEmptyMap()));
    }

}
