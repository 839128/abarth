package org.miaixz.bus.cron;


import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ThreadKit;
import org.miaixz.bus.cron.crontab.InvokeCrontab;

/**
 * 定时任务样例
 */
public class JobMainTest {

    public static void main(final String[] args) {
        Builder.setMatchSecond(true);
        Builder.start(false);

        /********************************/
        // 测试守护线程是否对作业线程有效
        Builder.schedule("*/2 * * * * *", new InvokeCrontab("org.miaixz.bus.cron.TestJob.doWhileTest"));
        // 当为守护线程时，stop方法调用后doWhileTest里的循环输出将终止，表示作业线程正常结束
        // 当非守护线程时，stop方法调用后，不再产生新的作业，原作业正常执行。
        Builder.setMatchSecond(true);
        Builder.start(true);

        ThreadKit.sleep(3000);
        Builder.stop();

        /********************************/

        Builder.setMatchSecond(true);
        Builder.start(false);
        Builder.getScheduler().clear();
        final String id = Builder.schedule("*/2 * * * * *", (Runnable) () -> Console.log("task running : 2s"));
        ThreadKit.sleep(3000);
        Builder.remove(id);
        Console.log("Task Removed");

        Builder.schedule("*/3 * * * * *", (Runnable) () -> Console.log("New task add running : 3s"));
        Console.log("New Task added.");

    }

}
