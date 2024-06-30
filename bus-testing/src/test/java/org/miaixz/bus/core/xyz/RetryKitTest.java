package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.lang.thread.RetryableTask;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RetryKitTest {

    @Test
    @Disabled
    void test() {
        //自定义根据异常重试
        final CompletableFuture<RetryableTask<String>> task = RetryableTask.retryForExceptions(() -> {
                    Console.log("1231231");
                    final int a = 1 / 0;
                    return String.valueOf(a);
                }, ArithmeticException.class)
                .delay(Duration.ofSeconds(1))
                .maxAttempts(3)
                .asyncExecute();

        Assertions.assertFalse(task.isDone());
        Assertions.assertEquals("兜底", task.join().get().orElse("兜底"));
        Assertions.assertTrue(task.isDone());

    }


    @Test
    @Disabled
    public void noReturnTest() {
        //根据异常重试，没有返回值
        RetryKit.ofException(
                () -> {
                    Console.log(123);
                    final int a = 1 / 0;
                    Console.log(a);
                },
                3,
                Duration.ofSeconds(1),
                () -> {
                    Console.log("兜底");
                },
                ArithmeticException.class
        );
    }


    @Test
    @Disabled
    public void hasReturnTest() {
        //根据自定义策略重试
        final String result = RetryKit.ofPredicate(
                () -> {
                    Console.log(123);
                    return "ok";
                },
                5,
                Duration.ofSeconds(1),
                () -> {
                    Console.log("兜底");
                    return "do";
                },
                (r, e) -> {
                    Console.log("r = " + r);
                    Console.log("e = " + e);
                    return r.equals("ok");
                }
        );

        Assertions.assertEquals("ok", result);
    }

}
