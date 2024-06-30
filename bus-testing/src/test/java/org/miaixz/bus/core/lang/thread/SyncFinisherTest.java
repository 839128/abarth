package org.miaixz.bus.core.lang.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.core.xyz.ThreadKit;

import java.util.concurrent.atomic.AtomicBoolean;

public class SyncFinisherTest {

    @Test
    void executeExceptionTest() {
        final AtomicBoolean hasException = new AtomicBoolean(false);
        final SyncFinisher syncFinisher = new SyncFinisher(10);
        syncFinisher.addWorker(() -> {
            Console.log(Integer.parseInt("XYZ"));//这里会抛RuntimeException
        });

        syncFinisher.setExceptionHandler((t, e) -> {
            hasException.set(true);
            Assertions.assertEquals("For input string: \"XYZ\"", e.getMessage());
        });

        syncFinisher.start();
        syncFinisher.close();
        ThreadKit.sleep(300);
        Assertions.assertTrue(hasException.get());
    }

}
