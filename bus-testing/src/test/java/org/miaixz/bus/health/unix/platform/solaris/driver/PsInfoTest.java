package org.miaixz.bus.health.unix.platform.solaris.driver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.miaixz.bus.core.center.regex.Pattern;
import org.miaixz.bus.health.Parsing;
import org.miaixz.bus.health.Platform;
import org.miaixz.bus.health.unix.jna.SolarisLibc;

import java.io.File;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnabledOnOs(OS.SOLARIS)
class PsInfoTest {

    @Test
    void testQueryPsInfo() {
        int pid = new Platform().getOperatingSystem().getProcessId();
        SolarisLibc.SolarisPsInfo psinfo = PsInfo.queryPsInfo(pid);
        assertThat("Process ID in structure should match PID", psinfo.pr_pid, is(pid));
        File directory = new File(String.format(Locale.ROOT, "/proc/%d/lwp", pid));
        File[] numericFiles = directory.listFiles(file -> Pattern.NUMBERS_PATTERN.matcher(file.getName()).matches());
        assertNotNull(numericFiles);
        for (File lwpidFile : numericFiles) {
            int tid = Parsing.parseIntOrDefault(lwpidFile.getName(), 0);
            SolarisLibc.SolarisLwpsInfo lwpsinfo = PsInfo.queryLwpsInfo(pid, tid);
            assertThat("Thread ID in structure should match TID", lwpsinfo.pr_lwpid, is(tid));
        }
    }

}
