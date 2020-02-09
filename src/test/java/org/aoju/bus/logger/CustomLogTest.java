package org.aoju.bus.logger;

import org.aoju.bus.logger.dialect.commons.ApacheCommonsLogFactory;
import org.aoju.bus.logger.dialect.console.ConsoleLogFactory;
import org.aoju.bus.logger.dialect.jboss.JbossLogFactory;
import org.aoju.bus.logger.dialect.jdk.JdkLogFactory;
import org.aoju.bus.logger.dialect.log4j.Log4jLogFactory;
import org.aoju.bus.logger.dialect.log4j2.Log4j2LogFactory;
import org.aoju.bus.logger.dialect.slf4j.Slf4jLogFactory;
import org.aoju.bus.logger.dialect.tinylog.TinyLogFactory;
import org.junit.jupiter.api.Test;

/**
 * 日志门面单元测试
 */
public class CustomLogTest {

    private static final String LINE = "---------------------";

    @Test
    public void consoleLogTest() {
        LogFactory factory = new ConsoleLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void commonsLogTest() {
        LogFactory factory = new ApacheCommonsLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void tinyLogTest() {
        LogFactory factory = new TinyLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void log4j2LogTest() {
        LogFactory factory = new Log4j2LogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void log4jLogTest() {
        LogFactory factory = new Log4jLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);

    }

    @Test
    public void jbossLogTest() {
        LogFactory factory = new JbossLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void jdkLogTest() {
        LogFactory factory = new JdkLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

    @Test
    public void slf4jTest() {
        LogFactory factory = new Slf4jLogFactory();
        LogFactory.setCurrentLogFactory(factory);
        Log log = LogFactory.get();

        log.info(null);
        log.info("This is custom '{}' log\n{}", factory.getName(), LINE);
    }

}
