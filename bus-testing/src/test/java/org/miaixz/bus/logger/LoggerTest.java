package org.miaixz.bus.logger;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.logger.metric.console.ConsoleColorFactory;
import org.miaixz.bus.logger.metric.console.ConsoleFactory;
import org.miaixz.bus.logger.metric.log4j.Log4jFactory;

public class LoggerTest {

    private static final Supplier log = Supplier.get();

    @Test
    void infoTest() {
        log.info("Static final Logger test");
    }

    @Test
    public void staticLog4j2Test() {
        Holder.setFactory(Log4jFactory.class);
        Logger.debug("This is static {} log", "debug");
        Logger.info("This is static {} log", "info");
    }

    @Test
    public void test() {
        Holder.setFactory(ConsoleFactory.class);
        Logger.debug("This is static {} log", "debug");
        Logger.info("This is static {} log", "info");
    }

    @Test
    public void colorTest() {
        Holder.setFactory(ConsoleColorFactory.class);
        Logger.debug("This is static {} log", "debug");
        Logger.info("This is static {} log", "info");
        Logger.error("This is static {} log", "error");
        Logger.warn("This is static {} log", "warn");
        Logger.trace("This is static {} log", "trace");
    }

}
