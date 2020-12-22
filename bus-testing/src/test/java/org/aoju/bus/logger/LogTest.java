package org.aoju.bus.logger;

import org.aoju.bus.logger.level.Level;
import org.junit.Test;

/**
 * 日志门面单元测试
 */
public class LogTest {

    @Test
    public void logTest() {
        Log log = LogFactory.get();

        // 自动选择日志实现
        log.debug("This is {} log", Level.DEBUG);
        log.info("This is {} log", Level.INFO);
        log.warn("This is {} log", Level.WARN);

    }

    /**
     * 兼容slf4j日志消息格式测试，即第二个参数是异常对象时正常输出异常信息
     */
    @Test
    public void logWithExceptionTest() {
        Log log = LogFactory.get();
        Exception e = new Exception("test Exception");
        log.error("我是错误消息", e);
    }

}
