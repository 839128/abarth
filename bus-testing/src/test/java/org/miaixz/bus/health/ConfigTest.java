package org.miaixz.bus.health;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Locale;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;


@Execution(ExecutionMode.SAME_THREAD)
class ConfigTest {
    private static final String PROPERTY = "oshi.test.property";
    private static final double EPSILON = Double.MIN_VALUE;

    @BeforeEach
    void setUp() {
        Config.clear();
    }

    @Test
    void testGetString() {
        GlobalConfigAsserter.asserter(PROPERTY).assertDefaultThat(null, null);
        Config.set(PROPERTY, "test");
        GlobalConfigAsserter.asserter(PROPERTY).assertThat("test", null);
        Config.set(PROPERTY, 123);

        GlobalConfigAsserter.asserter(PROPERTY).assertThat("123", null);
    }

    @Test
    void testGetInteger() {
        GlobalConfigAsserter.asserter(PROPERTY).assertDefaultThat(0, 0);
        Config.set(PROPERTY, 123);
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(123, 0).assertThat("123", null);

        // Invalid integer
        Config.set(PROPERTY, "1.23");
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(0, 0);
    }

    @Test
    void testGetDouble() {
        GlobalConfigAsserter.asserter(PROPERTY).assertDefaultThat(0.0, 0.0);
        Config.set(PROPERTY, 1.23d);
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(1.23, 0.0).assertThat("1.23", null);

        // Invalid double
        Config.set(PROPERTY, "1.2.3");
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(0.0, 0.0);
    }

    @Test
    void testGetBoolean() {
        GlobalConfigAsserter.asserter(PROPERTY).assertDefaultThat(false, false);
        Config.set(PROPERTY, true);
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(true, false).assertThat("true", null);
    }

    @Test
    void testSetNull() {
        Config.set(PROPERTY, "test");
        Config.set(PROPERTY, null);
        GlobalConfigAsserter.asserter(PROPERTY).assertThat("123", "123");
    }

    @Test
    void testRemove() {
        String removed = "test";
        Config.set(PROPERTY, removed);
        Config.remove(PROPERTY);
        GlobalConfigAsserter.asserter(PROPERTY).assertThat(String.format(Locale.ROOT, "Should have removed property %s", removed), "123", "123");
    }

    @Test
    void testLoad() {
        Config.load(propertiesWith("321"));

        GlobalConfigAsserter.asserter(PROPERTY).assertThat("321", null);
    }

    @Test
    void testPropertyExceptionMessage() {
        Config.set(PROPERTY, "test");
        assertThat(new Config.PropertyException(PROPERTY).getMessage(),
                Matchers.is(String.format(Locale.ROOT, "Invalid property: \"%s\" = test", PROPERTY)));
    }

    private Properties propertiesWith(String value) {
        Properties updates = new Properties();
        updates.setProperty(PROPERTY, value);
        return updates;
    }

    static final class GlobalConfigAsserter {

        private static final String FAILURE_MESSAGE_TEMPLATE = "property: %s value for def: %s should be";
        private static final String DEFAULT_FAILURE_MESSAGE_TEMPLATE = "Property: %s default value def: %s should be";
        private final String property;

        private GlobalConfigAsserter(String property) {
            this.property = property;
        }

        static GlobalConfigAsserter asserter(String property) {
            return new GlobalConfigAsserter(property);
        }

        GlobalConfigAsserter assertThat(Object expected, Object def) {
            assertThat(failureMessage(def), expected, def);
            return this;
        }

        GlobalConfigAsserter assertThat(String message, Object expected, Object def) {
            if (def instanceof String) {
                assertThat(message, Config.get(property, (String) def), Matchers.is(expected));
            } else if (def instanceof Boolean) {
                assertThat(message, Config.get(property, (boolean) def), Matchers.is(expected));
            } else if (def instanceof Integer) {
                assertThat(message, Config.get(property, (Integer) def), Matchers.is(expected));
            } else if (def instanceof Double) {
                assertThat(message, Config.get(property, (Double) def), Matchers.is(Matchers.closeTo((Double) expected, EPSILON)));
            }
            return this;
        }

        GlobalConfigAsserter assertDefaultThat(Object expected, Object def) {
            assertThat(defaultFailureMessage(def), expected, def);
            return this;
        }

        private String failureMessage(Object def) {
            return String.format(Locale.ROOT, FAILURE_MESSAGE_TEMPLATE, property, def);
        }

        private String defaultFailureMessage(Object def) {
            return String.format(Locale.ROOT, DEFAULT_FAILURE_MESSAGE_TEMPLATE, PROPERTY, def);
        }
    }

}
