package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CompareKitTest {

    @Test
    public void compareTest() {
        int compare = CompareKit.compare(null, "a", true);
        Assertions.assertTrue(compare > 0);

        compare = CompareKit.compare(null, "a", false);
        Assertions.assertTrue(compare < 0);
    }

    @Test
    public void comparingPinyin() {
        final List<String> list = ListKit.of("成都", "北京", "上海", "深圳");

        final List<String> ascendingOrderResult = ListKit.view("北京", "成都", "上海", "深圳");
        final List<String> descendingOrderResult = ListKit.view("深圳", "上海", "成都", "北京");

        // 正序
        list.sort(CompareKit.comparingPinyin(e -> e));
        Assertions.assertEquals(list, ascendingOrderResult);

        // 反序
        list.sort(CompareKit.comparingPinyin(e -> e, true));
        Assertions.assertEquals(list, descendingOrderResult);
    }

    @Test
    public void comparingIndexedTest() {
        final List<String> data = ListKit.of("1", "2", "3", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        final List<String> index = ListKit.view("2", "1", "3", "4");

        data.sort(CompareKit.comparingIndexed(e -> e, index));
        //[1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        Assertions.assertEquals(ListKit.view("5", "6", "7", "8", "9", "10", "2", "2", "1", "1", "3", "3", "4", "4"), data);
    }

    @Test
    public void comparingIndexedTest2() {
        final List<String> data = ListKit.of("1", "2", "3", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        final List<String> index = ListKit.view("2", "1", "3", "4");

        //正确排序，index.toArray()
        data.sort(CompareKit.comparingIndexed(e -> e, index.toArray()));
        //[5, 6, 7, 8, 9, 10, 2, 2, 1, 1, 3, 3, 4, 4]
        Assertions.assertEquals(ListKit.view("5", "6", "7", "8", "9", "10", "2", "2", "1", "1", "3", "3", "4", "4"), data);
    }

    @Test
    public void comparingIndexedTest3() {
        final List<String> data = ListKit.of("1", "2", "3", "4", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        final String[] indexArray = new String[]{"2", "1", "3", "4"};

        //正确排序，array
        data.sort(CompareKit.comparingIndexed(e -> e, indexArray));
        //[5, 6, 7, 8, 9, 10, 2, 2, 1, 1, 3, 3, 4, 4]
        Assertions.assertEquals(data, ListKit.view("5", "6", "7", "8", "9", "10", "2", "2", "1", "1", "3", "3", "4", "4"));
    }

    @Test
    public void compareNullTest() {
        Assertions.assertEquals(0, CompareKit.compare(1, 1));
        Assertions.assertEquals(1, CompareKit.compare(1, null));
        Assertions.assertEquals(-1, CompareKit.compare(null, 1));

        Assertions.assertEquals(-1, CompareKit.compare(1, null, true));
        Assertions.assertEquals(1, CompareKit.compare(null, 1, true));
    }
}
