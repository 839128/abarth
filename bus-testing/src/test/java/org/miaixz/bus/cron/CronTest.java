package org.miaixz.bus.cron;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ThreadKit;
import org.miaixz.bus.cron.crontab.Crontab;
import org.miaixz.bus.cron.listener.TaskListener;

/**
 * 定时任务样例
 */
public class CronTest {

    @Test
    @Disabled
    public void customCronTest() {
        Builder.schedule("*/2 * * * * *", (Crontab) () -> Console.log("Task excuted."));

        // 支持秒级别定时任务
        Builder.setMatchSecond(true);
        Builder.start();

        ThreadKit.waitForDie();
        Console.log("Exit.");
    }

    @Test
    @Disabled
    public void cronTest() {
        // 支持秒级别定时任务
        Builder.setMatchSecond(true);
        Builder.getScheduler().setDaemon(false);
        Builder.start();

        ThreadKit.waitForDie();
        Builder.stop();
    }

    @Test
    @Disabled
    public void cronWithListenerTest() {
        Builder.getScheduler().addListener(new TaskListener() {
            @Override
            public void onStart(final Executor executor) {
                Console.log("Found task:[{}] start!", executor.getCronTask().getId());
            }

            @Override
            public void onSucceeded(final Executor executor) {
                Console.log("Found task:[{}] success!", executor.getCronTask().getId());
            }

            @Override
            public void onFailed(final Executor executor, final Throwable exception) {
                Console.error("Found task:[{}] failed!", executor.getCronTask().getId());
            }
        });

        // 支持秒级别定时任务
        Builder.setMatchSecond(true);
        Builder.start();

        ThreadKit.waitForDie();
        Console.log("Exit.");
    }

    @Test
    @Disabled
    public void addAndRemoveTest() {
        final String id = Builder.schedule("*/2 * * * * *", (Runnable) () -> Console.log("task running : 2s"));

        Console.log(id);
        Builder.remove(id);

        // 支持秒级别定时任务
        Builder.setMatchSecond(true);
        Builder.start();
    }

}
