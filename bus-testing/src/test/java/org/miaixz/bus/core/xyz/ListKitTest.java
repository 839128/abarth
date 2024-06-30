package org.miaixz.bus.core.xyz;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.date.StopWatch;
import org.miaixz.bus.core.lang.Console;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListKitTest {

    @Test
    public void partitionTest() {
        List<List<Object>> lists = ListKit.partition(null, 3);
        assertEquals(ListKit.empty(), lists);

        lists = ListKit.partition(Arrays.asList(1, 2, 3, 4), 1);
        assertEquals("[[1], [2], [3], [4]]", lists.toString());
        lists = ListKit.partition(Arrays.asList(1, 2, 3, 4), 2);
        assertEquals("[[1, 2], [3, 4]]", lists.toString());
        lists = ListKit.partition(Arrays.asList(1, 2, 3, 4), 3);
        assertEquals("[[1, 2, 3], [4]]", lists.toString());
        lists = ListKit.partition(Arrays.asList(1, 2, 3, 4), 4);
        assertEquals("[[1, 2, 3, 4]]", lists.toString());
        lists = ListKit.partition(Arrays.asList(1, 2, 3, 4), 5);
        assertEquals("[[1, 2, 3, 4]]", lists.toString());
    }

    @Test
    @Disabled
    public void partitionBenchTest() {
        final List<String> list = new ArrayList<>();
        CollKit.padRight(list, RandomKit.randomInt(1000_0000, 1_0000_0000), "test");

        final int size = RandomKit.randomInt(10, 1000);
        Console.log("\nlist size: {}", list.size());
        Console.log("partition size: {}\n", size);
        final StopWatch stopWatch = new StopWatch();

        stopWatch.start("CollKit#split");
        final List<List<String>> CollSplitResult = CollKit.partition(list, size);
        stopWatch.stop();

        stopWatch.start("ListKit#split");
        final List<List<String>> ListSplitResult = ListKit.partition(list, size);
        stopWatch.stop();

        assertEquals(CollSplitResult, ListSplitResult);

        Console.log(stopWatch.prettyPrint());
    }

    @Test
    public void splitAvgTest() {
        List<List<Object>> lists = ListKit.avgPartition(null, 3);
        assertEquals(ListKit.empty(), lists);

        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3, 4), 1);
        assertEquals("[[1, 2, 3, 4]]", lists.toString());
        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3, 4), 2);
        assertEquals("[[1, 2], [3, 4]]", lists.toString());
        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3, 4), 3);
        assertEquals("[[1, 2], [3], [4]]", lists.toString());
        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3, 4), 4);
        assertEquals("[[1], [2], [3], [4]]", lists.toString());

        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3), 5);
        assertEquals("[[1], [2], [3], [], []]", lists.toString());
        lists = ListKit.avgPartition(Arrays.asList(1, 2, 3), 2);
        assertEquals("[[1, 2], [3]]", lists.toString());
    }

    @Test
    public void splitAvgNotZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            // limit不能小于等于0
            ListKit.avgPartition(Arrays.asList(1, 2, 3, 4), 0);
        });
    }

    @Test
    public void editTest() {
        final List<String> a = ListKit.ofLinked("1", "2", "3");
        final List<String> filter = CollKit.edit(a, str -> "edit" + str);
        assertEquals("edit1", filter.get(0));
        assertEquals("edit2", filter.get(1));
        assertEquals("edit3", filter.get(2));
    }

    @Test
    public void indexOfAll() {
        final List<String> a = ListKit.ofLinked("1", "2", "3", "4", "3", "2", "1");
        final int[] indexArray = CollKit.indexOfAll(a, "2"::equals);
        Assertions.assertArrayEquals(new int[]{1, 5}, indexArray);
        final int[] indexArray2 = CollKit.indexOfAll(a, "1"::equals);
        Assertions.assertArrayEquals(new int[]{0, 6}, indexArray2);
    }

    @Test
    public void pageTest1() {
        final List<Integer> a = ListKit.ofLinked(1, 2, 3, 4, 5);

        final int[] a_1 = ListKit.page(0, 2, a).stream().mapToInt(Integer::valueOf).toArray();
        final int[] a1 = ListKit.page(0, 2, a).stream().mapToInt(Integer::valueOf).toArray();
        final int[] a2 = ListKit.page(1, 2, a).stream().mapToInt(Integer::valueOf).toArray();
        final int[] a3 = ListKit.page(2, 2, a).stream().mapToInt(Integer::valueOf).toArray();
        final int[] a4 = ListKit.page(3, 2, a).stream().mapToInt(Integer::valueOf).toArray();
        Assertions.assertArrayEquals(new int[]{1, 2}, a_1);
        Assertions.assertArrayEquals(new int[]{1, 2}, a1);
        Assertions.assertArrayEquals(new int[]{3, 4}, a2);
        Assertions.assertArrayEquals(new int[]{5}, a3);
        Assertions.assertArrayEquals(new int[]{}, a4);
    }

    @Test
    public void subTest() {
        final List<Integer> of = ListKit.view(1, 2, 3, 4);
        final List<Integer> sub = ListKit.sub(of, 2, 4);
        sub.remove(0);

        // 对子列表操作不影响原列表
        assertEquals(4, of.size());
        assertEquals(1, sub.size());
    }

    @Test
    public void sortByPropertyTest() {
        @Data
        @AllArgsConstructor
        class TestBean {
            private int order;
            private String name;
        }

        final List<TestBean> beanList = ListKit.of(
                new TestBean(2, "test2"),
                new TestBean(1, "test1"),
                new TestBean(5, "test5"),
                new TestBean(4, "test4"),
                new TestBean(3, "test3")
        );

        final List<TestBean> order = ListKit.sortByProperty(beanList, "order");
        assertEquals("test1", order.get(0).getName());
        assertEquals("test2", order.get(1).getName());
        assertEquals("test3", order.get(2).getName());
        assertEquals("test4", order.get(3).getName());
        assertEquals("test5", order.get(4).getName());
    }

    @Test
    public void swapIndex() {
        final List<Integer> list = Arrays.asList(7, 2, 8, 9);
        ListKit.swapTo(list, 8, 1);
        assertEquals(8, (int) list.get(1));
    }

    @Test
    public void swapElement() {
        final Map<String, String> map1 = new HashMap<>();
        map1.put("1", "张三");
        final Map<String, String> map2 = new HashMap<>();
        map2.put("2", "李四");
        final Map<String, String> map3 = new HashMap<>();
        map3.put("3", "王五");
        final List<Map<String, String>> list = Arrays.asList(map1, map2, map3);
        ListKit.swapElement(list, map2, map3);
        Map<String, String> map = list.get(2);
        assertEquals("李四", map.get("2"));

        ListKit.swapElement(list, map2, map1);
        map = list.get(0);
        assertEquals("李四", map.get("2"));
    }

    @Test
    public void addAllIfNotContainsTest() {
        final ArrayList<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        final ArrayList<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        ListKit.addAllIfNotContains(list1, list2);

        assertEquals(3, list1.size());
        assertEquals("1", list1.get(0));
        assertEquals("2", list1.get(1));
        assertEquals("3", list1.get(2));
    }

    @Test
    public void setOrPaddingNullTest() {
        final List<String> list = new ArrayList<>();
        list.add("1");

        // 替换原值
        ListKit.setOrPadding(list, 0, "a");
        assertEquals("[a]", list.toString());

        //append值
        ListKit.setOrPadding(list, 1, "a");
        assertEquals("[a, a]", list.toString());

        // padding null 后加入值
        ListKit.setOrPadding(list, 3, "a");
        assertEquals(4, list.size());
    }

    @Test
    public void ofCopyOnWriteTest() {
        final CopyOnWriteArrayList<String> strings = ListKit.ofCopyOnWrite(ListKit.of("a", "b"));
        assertEquals(2, strings.size());
    }

    @Test
    public void ofCopyOnWriteTest2() {
        final CopyOnWriteArrayList<String> strings = ListKit.ofCopyOnWrite("a", "b");
        assertEquals(2, strings.size());
    }

    @Test
    void reverseNewTest() {
        final List<Integer> view = ListKit.view(1, 2, 3);
        final List<Integer> reverse = ListKit.reverseNew(view);
        assertEquals("[3, 2, 1]", reverse.toString());
    }

    @Test
    void reverseNewTest2() {
        final List<Integer> list = ListKit.of(1, 2, 3);
        ListKit.reverseNew(list);
    }

    @Test
    public void testMoveElementToPosition() {
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

        // Move "B" to position 2
        final List<String> expectedResult1 = new ArrayList<>(Arrays.asList("A", "C", "B", "D"));
        assertEquals(expectedResult1, ListKit.move(list, "B", 2));

        list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

        // Move "D" to position 0
        final List<String> expectedResult2 = new ArrayList<>(Arrays.asList("D", "A", "B", "C"));
        assertEquals(expectedResult2, ListKit.move(list, "D", 0));

        list = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));

        // Move "E" (not in list) to position 1
        final List<String> expectedResult3 = new ArrayList<>(Arrays.asList("A", "E", "B", "C", "D"));
        assertEquals(expectedResult3, ListKit.move(list, "E", 1));
    }
}
