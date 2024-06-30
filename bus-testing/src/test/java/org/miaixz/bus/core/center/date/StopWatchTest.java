package org.miaixz.bus.core.center.date;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {

    @Test
    public void prettyPrintTest() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start("任务1");
        ThreadKit.sleep(1);
        stopWatch.stop();
        stopWatch.start("任务2");
        ThreadKit.sleep(200);
        stopWatch.stop();

        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

}
