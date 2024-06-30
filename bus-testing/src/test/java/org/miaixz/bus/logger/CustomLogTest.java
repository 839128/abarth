package org.miaixz.bus.logger;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.logger.metric.commons.CommonsFactory;
import org.miaixz.bus.logger.metric.console.ConsoleFactory;
import org.miaixz.bus.logger.metric.jboss.JbossFactory;
import org.miaixz.bus.logger.metric.jdk.JdkFactory;
import org.miaixz.bus.logger.metric.log4j.Log4jFactory;
import org.miaixz.bus.logger.metric.slf4j.Slf4jFactory;
import org.miaixz.bus.logger.metric.tinylog.TinyLogFactory;

/**
 * 日志门面单元测试
 */
public class CustomLogTest {

    private static final String LINE = "----------------------------------------------------------------------";

    @Test
    public void consoleLogTest() {
        final Factory factory = new ConsoleFactory();
        Holder.setFactory(factory);
        final Supplier log = Supplier.get();

        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void consoleLogNullTest() {
        final Factory factory = new ConsoleFactory();
        Holder.setFactory(factory);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
    }

    @Test
    public void commonsLogTest() {
        final Factory engine = new CommonsFactory();
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);
    }

    @Test
    public void tinyLog2Test() {
        final Factory engine = new TinyLogFactory();
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);
    }

    @Test
    public void log4jLogTest() {
        final Factory engine = new Log4jFactory();
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);

    }

    @Test
    public void jbossLogTest() {
        final Factory engine = new JbossFactory();
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);
    }

    @Test
    public void jdkLogTest() {
        final Factory engine = new JdkFactory();
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);
    }

    @Test
    public void slf4jTest() {
        final Factory engine = new Slf4jFactory(false);
        Holder.setFactory(engine);
        final Supplier log = Supplier.get();

        log.info(null);
        log.info((String) null);
        log.info("This is custom '{}' log\n{}", engine.getName(), LINE);
    }

}
