package org.miaixz.bus.cron;

import org.miaixz.bus.core.data.id.ID;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.DateKit;
import org.miaixz.bus.core.xyz.ThreadKit;

/**
 * 测试定时任务，当触发到定时的时间点时，执行doTest方法
 */
public class TestJob {

    private final String jobId = ID.simpleUUID();

    /**
     * 执行定时任务内容
     */
    public void doTest() {
        Console.log("Test Job {} running... at {}", jobId, DateKit.formatNow());
    }

    /**
     * 执行循环定时任务，测试在定时任务结束时作为deamon线程是否能正常结束
     */
    public void doWhileTest() {
        final String name = Thread.currentThread().getName();
        while (true) {
            Console.log("Job {} while running...", name);
            ThreadKit.sleep(2000);
        }
    }

}
