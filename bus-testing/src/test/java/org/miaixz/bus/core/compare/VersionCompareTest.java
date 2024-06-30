package org.miaixz.bus.core.compare;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 版本比较单元测试
 */
public class VersionCompareTest {

    @Test
    public void compareEmptyTest() {
        int compare = VersionCompare.INSTANCE.compare("", "1.12.1");
        assertTrue(compare < 0);

        compare = VersionCompare.INSTANCE.compare("", null);
        assertTrue(compare > 0);
        compare = VersionCompare.INSTANCE.compare(null, "");
        assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTest1() {
        int compare = VersionCompare.INSTANCE.compare("1.2.1", "1.12.1");
        assertTrue(compare < 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("1.12.1", "1.2.1");
        assertTrue(compare > 0);
    }

    @Test
    public void versionComparatorTest2() {
        int compare = VersionCompare.INSTANCE.compare("1.12.1", "1.12.1c");
        assertTrue(compare < 0);

        compare = VersionCompare.INSTANCE.compare("1.12.1c", "1.12.1");
        assertTrue(compare > 0);
    }

    @Test
    public void versionComparatorTest3() {
        int compare = VersionCompare.INSTANCE.compare(null, "1.12.1c");
        assertTrue(compare < 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("1.12.1c", null);
        assertTrue(compare > 0);
    }

    @Test
    public void versionComparatorTest4() {
        int compare = VersionCompare.INSTANCE.compare("1.13.0", "1.12.1c");
        assertTrue(compare > 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("1.12.1c", "1.13.0");
        assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTest5() {
        int compare = VersionCompare.INSTANCE.compare("V1.2", "V1.1");
        assertTrue(compare > 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("V1.1", "V1.2");
        assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTes6() {
        int compare = VersionCompare.INSTANCE.compare("V0.0.20170102", "V0.0.20170101");
        assertTrue(compare > 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("V0.0.20170101", "V0.0.20170102");
        assertTrue(compare < 0);
    }

    @Test
    public void equalsTest() {
        final VersionCompare first = new VersionCompare();
        final VersionCompare other = new VersionCompare();
        Assertions.assertNotEquals(first, other);
    }

    @Test
    public void versionComparatorTest7() {
        int compare = VersionCompare.INSTANCE.compare("1.12.2", "1.12.1c");
        assertTrue(compare > 0);

        // 自反测试
        compare = VersionCompare.INSTANCE.compare("1.12.1c", "1.12.2");
        assertTrue(compare < 0);
    }

    @Test
    void equalsTest2() {
        final int compare = VersionCompare.INSTANCE.compare("1.12.0", "1.12");
        Assertions.assertEquals(0, compare);
    }

    @Test
    void I8Z3VETest() {
        // 传递性测试
        int compare = VersionCompare.INSTANCE.compare("260", "a-34");
        assertTrue(compare > 0);
        compare = VersionCompare.INSTANCE.compare("a-34", "a-3");
        assertTrue(compare > 0);
        compare = VersionCompare.INSTANCE.compare("260", "a-3");
        assertTrue(compare > 0);
    }

    @Test
    void startWithNoneNumberTest() {
        final int compare = VersionCompare.INSTANCE.compare("V1", "A1");
        assertTrue(compare > 0);
    }

    @Test
    void compareFileNameTest() {
        final String[] a = {"abc2.doc", "abc1.doc", "abc12.doc"};
        Arrays.sort(a, VersionCompare.INSTANCE);

        assertArrayEquals(new String[]{"abc1.doc", "abc2.doc", "abc12.doc"}, a);
    }
}
