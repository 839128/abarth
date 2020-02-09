package org.aoju.bus.logger;

import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    public void test() {
        Logger.debug("This is static {} log", "debug");
        Logger.info("This is static {} log", "info");
    }

}
