package org.miaixz.bus.health.unix.platform.aix.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.center.regex.Pattern;
import org.miaixz.bus.core.lang.tuple.Pair;
import org.miaixz.bus.core.lang.tuple.Triplet;
import org.miaixz.bus.health.Parsing;
import org.miaixz.bus.health.Platform;
import org.miaixz.bus.health.unix.jna.AixLibc;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnabledOnOs(OS.AIX)
class PsInfoTest {

    @Test
    void testQueryPsInfo() {
        int pid = new Platform().getOperatingSystem().getProcessId();
        AixLibc.AixPsInfo psinfo = PsInfo.queryPsInfo(pid);
        assertThat("Process ID in structure should match PID", psinfo.pr_pid, is((long) pid));

        Triplet<Integer, Long, Long> addrs = PsInfo.queryArgsEnvAddrs(pid, psinfo);
        assertNotNull(addrs);
        Pair<List<String>, Map<String, String>> argsEnv = PsInfo.queryArgsEnv(pid, psinfo);
        assertThat("Arg list size should match argc", argsEnv.getLeft().size(), is(addrs.getLeft().intValue()));

        File directory = new File(String.format(Locale.ROOT, "/proc/%d/lwp", pid));
        File[] numericFiles = directory.listFiles(file -> Pattern.NUMBERS_PATTERN.matcher(file.getName()).matches());
        assertNotNull(numericFiles);
        for (File lwpidFile : numericFiles) {
            int tid = Parsing.parseIntOrDefault(lwpidFile.getName(), 0);
            AixLibc.AixLwpsInfo lwpsinfo = PsInfo.queryLwpsInfo(pid, tid);
            assertThat("Thread ID in structure should match TID", lwpsinfo.pr_lwpid, is((long) tid));
        }
    }

}
