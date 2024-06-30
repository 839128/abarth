package org.miaixz.bus.core.compare;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Assert;
import org.miaixz.bus.core.xyz.ListKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorerCompareTest {

    final List<String> answer1 = ListKit.of(
            "filename",
            "filename 00",
            "filename 0",
            "filename 01",
            "filename.jpg",
            "filename.txt",
            "filename00.jpg",
            "filename00a.jpg",
            "filename00a.txt",
            "filename0",
            "filename0.jpg",
            "filename0a.txt",
            "filename0b.jpg",
            "filename0b1.jpg",
            "filename0b02.jpg",
            "filename0c.jpg",
            "filename01.0hjh45-test.txt",
            "filename01.0hjh46",
            "filename01.1hjh45.txt",
            "filename01.hjh45.txt",
            "Filename01.jpg",
            "Filename1.jpg",
            "filename2.hjh45.txt",
            "filename2.jpg",
            "filename03.jpg",
            "filename3.jpg"
    );

    List<String> answer2 = ListKit.of(
            "abc1.doc",
            "abc2.doc",
            "abc12.doc"
    );

    @Test
    public void testCompare1() {
        final List<String> toSort = new ArrayList<>(answer1);
        while (toSort.equals(answer1)) {
            Collections.shuffle(toSort);
        }
        toSort.sort(ExplorerCompare.INSTANCE);
        Assert.equals(toSort, answer1);
    }

    @Test
    public void testCompare2() {
        final List<String> toSort = new ArrayList<>(answer2);
        while (toSort.equals(answer2)) {
            Collections.shuffle(toSort);
        }
        toSort.sort(ExplorerCompare.INSTANCE);
        Assert.equals(toSort, answer2);
    }

}
