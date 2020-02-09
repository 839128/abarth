package org.aoju.bus.core.compare;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 版本比较单元测试
 */
public class VersionCompareTest {

    @Test
    public void versionComparatorTest1() {
        int compare = VersionCompare.INSTANCE.compare("1.2.1", "1.12.1");
        Assertions.assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTest2() {
        int compare = VersionCompare.INSTANCE.compare("1.12.1", "1.12.1c");
        Assertions.assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTest3() {
        int compare = VersionCompare.INSTANCE.compare(null, "1.12.1c");
        Assertions.assertTrue(compare < 0);
    }

    @Test
    public void versionComparatorTest4() {
        int compare = VersionCompare.INSTANCE.compare("1.13.0", "1.12.1c");
        Assertions.assertTrue(compare > 0);
    }

    @Test
    public void versionComparatorTest5() {
        int compare = VersionCompare.INSTANCE.compare("V1.2", "V1.1");
        Assertions.assertTrue(compare > 0);
    }

    @Test
    public void versionComparatorTes6() {
        int compare = VersionCompare.INSTANCE.compare("V0.0.20200102", "V0.0.20200101");
        Assertions.assertTrue(compare > 0);
    }

}
