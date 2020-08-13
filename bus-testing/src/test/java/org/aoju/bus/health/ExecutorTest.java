package org.aoju.bus.health;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test command line and returning the result of execution.
 */
public class ExecutorTest {

    private static final String ECHO = Builder.getOs().equals(Platform.OS.WINDOWS)
            ? "cmd.exe /C echo Test"
            : "echo Test";
    private static final String BAD_COMMAND = "noOSshouldHaveACommandNamedThis";

    @Test
    public void testRunNative() {
        List<String> test = Executor.runNative(ECHO);
        assertEquals(1, test.size());
        assertEquals("Test", test.get(0));
        assertEquals("Test", Executor.getFirstAnswer(ECHO));

        assertTrue(Executor.runNative(BAD_COMMAND).isEmpty());
        assertTrue(Executor.getFirstAnswer(BAD_COMMAND).isEmpty());
    }
}
