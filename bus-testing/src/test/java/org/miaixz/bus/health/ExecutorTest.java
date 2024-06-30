package org.miaixz.bus.health;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test command line and returning the result of execution.
 */
class ExecutorTest {

    private static final String ECHO = Platform.getCurrentPlatform().equals(Platform.OS.WINDOWS)
            ? "cmd.exe /C echo Test"
            : "echo Test";
    private static final String BAD_COMMAND = "noOSshouldHaveACommandNamedThis";

    @Test
    void testRunNative() {
        List<String> test = Executor.runNative(ECHO);
        assertThat("echo output", test, is(1));
        assertThat("echo output", test.get(0), is("Test"));
        assertThat("echo first answer", Executor.getFirstAnswer(ECHO), is("Test"));

        assertThat("bad command", Executor.runNative(BAD_COMMAND), is(empty()));
        assertThat("bad command first answer", Executor.getFirstAnswer(BAD_COMMAND), is(emptyString()));
    }

}
