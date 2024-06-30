package org.miaixz.bus.core.xyz;

import lombok.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.lang.Console;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 集合工具类单元测试
 */
public class CollKitTest {

    private final List<String> strList = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

    @Test
    public void emptyIfNullTest() {
        final Set<?> set = null;
        final Set<?> set1 = CollKit.emptyIfNull(set);
        Assertions.assertEquals(SetKit.empty(), set1);

        final List<?> list = null;
        final List<?> list1 = CollKit.emptyIfNull(list);
        Assertions.assertEquals(ListKit.empty(), list1);
    }

    @Test
    public void hasNullTest() {
        List<Object> list = null;
        Assertions.assertTrue(CollKit.hasNull(list));

        list = ListKit.of();
        Assertions.assertFalse(CollKit.hasNull(list));

        list = ListKit.of("");
        Assertions.assertFalse(CollKit.hasNull(list));

        list = ListKit.of("", null);
        Assertions.assertTrue(CollKit.hasNull(list));
    }

    @Test
    public void defaultIfEmpty() {
        List<String> strings = CollKit.defaultIfEmpty(ListKit.of(), ListKit.of("1"));
        Assertions.assertEquals(ListKit.of("1"), strings);

        strings = CollKit.defaultIfEmpty(null, ListKit.of("1"));
        Assertions.assertEquals(ListKit.of("1"), strings);
    }

    @Test
    public void defaultIfEmpty2() {
        final List<String> strings = CollKit.defaultIfEmpty(ListKit.of(), Function.identity(), () -> ListKit.of("1"));
        Assertions.assertEquals(ListKit.of("1"), strings);
    }

    @Test
    public void testPredicateContains() {
        final List<String> list = ListKit.of("bbbbb", "aaaaa", "ccccc");
        Assertions.assertTrue(CollKit.contains(list, s -> s.startsWith("a")));
        Assertions.assertFalse(CollKit.contains(list, s -> s.startsWith("d")));
    }

    @Test
    public void testRemoveWithAddIf() {
        List<Integer> list = ListKit.of(1, 2, 3);
        final List<Integer> exceptRemovedList = ListKit.of(2, 3);
        final List<Integer> exceptResultList = ListKit.of(1);

        List<Integer> resultList = CollKit.removeWithAddIf(list, ele -> 1 == ele);
        Assertions.assertEquals(list, exceptRemovedList);
        Assertions.assertEquals(resultList, exceptResultList);

        list = ListKit.of(1, 2, 3);
        resultList = new ArrayList<>();
        CollKit.removeWithAddIf(list, resultList, ele -> 1 == ele);
        Assertions.assertEquals(list, exceptRemovedList);
        Assertions.assertEquals(resultList, exceptResultList);
    }

    @Test
    public void testPadLeft() {
        List<String> srcList = ListKit.of();
        List<String> answerList = ListKit.of("a", "b");
        CollKit.padLeft(srcList, 1, "b");
        CollKit.padLeft(srcList, 2, "a");
        Assertions.assertEquals(srcList, answerList);

        srcList = ListKit.of("a", "b");
        answerList = ListKit.of("a", "b");
        CollKit.padLeft(srcList, 2, "a");
        Assertions.assertEquals(srcList, answerList);

        srcList = ListKit.of("c");
        answerList = ListKit.of("a", "a", "c");
        CollKit.padLeft(srcList, 3, "a");
        Assertions.assertEquals(srcList, answerList);
    }

    @Test
    public void testPadRight() {
        final List<String> srcList = ListKit.of("a");
        final List<String> answerList = ListKit.of("a", "b", "b", "b", "b");
        CollKit.padRight(srcList, 5, "b");
        Assertions.assertEquals(srcList, answerList);
    }

    @Test
    public void isNotEmptyTest() {
        Assertions.assertFalse(CollKit.isNotEmpty((Collection<?>) null));
    }

    @Test
    public void newHashSetTest() {
        final Set<String> set = SetKit.of((String[]) null);
        Assertions.assertNotNull(set);
    }

    @Test
    public void unionTest() {
        final List<String> list1 = ListKit.of("a", "b", "b", "c", "d", "x");
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d");

        final Collection<String> union = CollKit.union(list1, list2);

        Assertions.assertEquals(3, CollKit.count(union, "b"::equals));
    }

    @Test
    public void intersectionTest() {
        final List<String> list1 = ListKit.of("a", "b", "b", "c", "d", "x");
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d");

        final Collection<String> intersection = CollKit.intersection(list1, list2);
        Assertions.assertEquals(2, CollKit.count(intersection, "b"::equals));
        Assertions.assertEquals(0, CollKit.count(intersection, "x"::equals));
    }

    @Test
    public void intersectionDistinctTest() {
        final List<String> list1 = ListKit.of("a", "b", "b", "c", "d", "x");
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d");
        final List<String> list3 = ListKit.of();

        final Collection<String> intersectionDistinct = CollKit.intersectionDistinct(list1, list2);
        Assertions.assertEquals(SetKit.ofLinked("a", "b", "c", "d"), intersectionDistinct);

        final Collection<String> intersectionDistinct2 = CollKit.intersectionDistinct(list1, list2, list3);
        Console.log(intersectionDistinct2);
        Assertions.assertTrue(intersectionDistinct2.isEmpty());
    }

    @Test
    public void disjunctionTest() {
        final List<String> list1 = ListKit.of("a", "b", "b", "c", "d", "x");
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d", "x2");

        final Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertTrue(disjunction.contains("b"));
        Assertions.assertTrue(disjunction.contains("x2"));
        Assertions.assertTrue(disjunction.contains("x"));

        final Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertTrue(disjunction2.contains("b"));
        Assertions.assertTrue(disjunction2.contains("x2"));
        Assertions.assertTrue(disjunction2.contains("x"));
    }

    @Test
    public void disjunctionTest2() {
        // 任意一个集合为空，差集为另一个集合
        final List<String> list1 = ListKit.of();
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d", "x2");

        final Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertEquals(list2, disjunction);
        final Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertEquals(list2, disjunction2);
    }

    @Test
    public void disjunctionTest3() {
        // 无交集下返回共同的元素
        final List<String> list1 = ListKit.of("1", "2", "3");
        final List<String> list2 = ListKit.of("a", "b", "c");

        final Collection<String> disjunction = CollKit.disjunction(list1, list2);
        Assertions.assertTrue(disjunction.contains("1"));
        Assertions.assertTrue(disjunction.contains("2"));
        Assertions.assertTrue(disjunction.contains("3"));
        Assertions.assertTrue(disjunction.contains("a"));
        Assertions.assertTrue(disjunction.contains("b"));
        Assertions.assertTrue(disjunction.contains("c"));
        final Collection<String> disjunction2 = CollKit.disjunction(list2, list1);
        Assertions.assertTrue(disjunction2.contains("1"));
        Assertions.assertTrue(disjunction2.contains("2"));
        Assertions.assertTrue(disjunction2.contains("3"));
        Assertions.assertTrue(disjunction2.contains("a"));
        Assertions.assertTrue(disjunction2.contains("b"));
        Assertions.assertTrue(disjunction2.contains("c"));
    }

    @Test
    public void subtractTest() {
        final List<String> list1 = ListKit.of("a", "b", "b", "c", "d", "x");
        final List<String> list2 = ListKit.of("a", "b", "b", "b", "c", "d", "x2");
        final Collection<String> subtract = CollKit.subtract(list1, list2);
        Assertions.assertEquals(1, subtract.size());
        Assertions.assertEquals("x", subtract.iterator().next());
    }

    @Test
    public void subtractSetTest() {
        final HashMap<String, Object> map1 = MapKit.newHashMap();
        final HashMap<String, Object> map2 = MapKit.newHashMap();
        map1.put("1", "v1");
        map1.put("2", "v2");
        map2.put("2", "v2");
        final Collection<String> r2 = CollKit.subtract(map1.keySet(), map2.keySet());
        Assertions.assertEquals("[1]", r2.toString());
    }

    @Test
    public void subtractSetToListTest() {
        final HashMap<String, Object> map1 = MapKit.newHashMap();
        final HashMap<String, Object> map2 = MapKit.newHashMap();
        map1.put("1", "v1");
        map1.put("2", "v2");
        map2.put("2", "v2");
        final List<String> r2 = CollKit.subtractToList(map1.keySet(), map2.keySet());
        Assertions.assertEquals("[1]", r2.toString());
    }

    @Test
    public void toMapListAndToListMapTest() {
        final HashMap<String, String> map1 = new HashMap<>();
        map1.put("a", "值1");
        map1.put("b", "值1");

        final HashMap<String, String> map2 = new HashMap<>();
        map2.put("a", "值2");
        map2.put("c", "值3");

        // ----------------------------------------------------------------------------------------
        final List<HashMap<String, String>> list = ListKit.of(map1, map2);
        final Map<String, List<String>> map = CollKit.toListMap(list);
        Assertions.assertEquals("值1", map.get("a").get(0));
        Assertions.assertEquals("值2", map.get("a").get(1));

        // ----------------------------------------------------------------------------------------
        final List<Map<String, String>> listMap = CollKit.toMapList(map);
        Assertions.assertEquals("值1", listMap.get(0).get("a"));
        Assertions.assertEquals("值2", listMap.get(1).get("a"));
    }

    @Test
    public void getFieldValuesTest() {
        final Dictionary v1 = Dictionary.of().set("id", 12).set("name", "张三").set("age", 23);
        final Dictionary v2 = Dictionary.of().set("age", 13).set("id", 15).set("name", "李四");
        final List<Dictionary> list = ListKit.of(v1, v2);

        final List<Object> fieldValues = (List<Object>) CollKit.getFieldValues(list, "name");

        Assertions.assertEquals("张三", fieldValues.get(0));
        Assertions.assertEquals("李四", fieldValues.get(1));
    }

    @Test
    public void partitionTest() {
        final List<Integer> list = ListKit.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final List<List<Integer>> split = CollKit.partition(list, 3);
        Assertions.assertEquals(3, split.size());
        Assertions.assertEquals(3, split.get(0).size());
    }

    @Test
    public void partitionTest2() {
        final ArrayList<Integer> list = ListKit.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final List<List<Integer>> split = CollKit.partition(list, Integer.MAX_VALUE);
        Assertions.assertEquals(1, split.size());
        Assertions.assertEquals(9, split.get(0).size());
    }

    @Test
    public void foreachTest() {
        final HashMap<String, String> map = MapKit.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        final String[] result = new String[1];
        final String a = "a";
        CollKit.forEach(map, (key, value, index) -> {
            if (a.equals(key)) {
                result[0] = value;
            }
        });
        Assertions.assertEquals("1", result[0]);
    }

    @Test
    public void editTest() {
        final List<String> list = ListKit.of("a", "b", "c");

        final Collection<String> filtered = CollKit.edit(list, t -> t + 1);

        Assertions.assertEquals(ListKit.of("a1", "b1", "c1"), filtered);
    }

    @Test
    public void removeTest() {
        final List<String> list = ListKit.of("a", "b", "c");

        final List<String> filtered = CollKit.remove(list, "a"::equals);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(ListKit.of("b", "c"), filtered);
    }

    @Test
    public void removeForSetTest() {
        final Set<String> set = SetKit.ofLinked("a", "b", "", "  ", "c");
        final Set<String> filtered = CollKit.remove(set, StringKit::isBlank);

        Assertions.assertEquals(SetKit.ofLinked("a", "b", "c"), filtered);
    }

    @Test
    public void filterRemoveTest() {
        final List<String> list = ListKit.of("a", "b", "c");

        final List<String> removed = new ArrayList<>();
        final List<String> filtered = CollKit.remove(list, t -> {
            if ("a".equals(t)) {
                removed.add(t);
                return true;
            }
            return false;
        });

        Assertions.assertEquals(1, removed.size());
        Assertions.assertEquals("a", removed.get(0));

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(ListKit.of("b", "c"), filtered);
    }

    @Test
    public void removeNullTest() {
        final List<String> list = ListKit.of("a", "b", "c", null, "", "  ");

        final List<String> filtered = CollKit.removeNull(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(ListKit.of("a", "b", "c", "", "  "), filtered);
    }

    @Test
    public void removeEmptyTest() {
        final List<String> list = ListKit.of("a", "b", "c", null, "", "  ");

        final List<String> filtered = CollKit.removeEmpty(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(ListKit.of("a", "b", "c", "  "), filtered);
    }

    @Test
    public void removeBlankTest() {
        final List<String> list = ListKit.of("a", "b", "c", null, "", "  ");

        final List<String> filtered = CollKit.removeBlank(list);

        // 原地过滤
        Assertions.assertSame(list, filtered);
        Assertions.assertEquals(ListKit.of("a", "b", "c"), filtered);
    }

    @Test
    public void groupTest() {
        final List<String> list = ListKit.of("1", "2", "3", "4", "5", "6");
        final List<List<String>> group = CollKit.group(list, null);
        Assertions.assertFalse(group.isEmpty());

        final List<List<String>> group2 = CollKit.group(list, t -> {
            // 按照奇数偶数分类
            return Integer.parseInt(t) % 2;
        });
        Assertions.assertEquals(ListKit.of("2", "4", "6"), group2.get(0));
        Assertions.assertEquals(ListKit.of("1", "3", "5"), group2.get(1));
    }

    @Test
    public void groupByFieldTest() {
        final List<TestBean> list = ListKit.of(new TestBean("张三", 12), new TestBean("李四", 13), new TestBean("王五", 12));
        final List<List<TestBean>> groupByField = CollKit.groupByField(list, "age");
        Assertions.assertEquals("张三", groupByField.get(0).get(0).getName());
        Assertions.assertEquals("王五", groupByField.get(0).get(1).getName());

        Assertions.assertEquals("李四", groupByField.get(1).get(0).getName());
    }

    @Test
    public void groupByFuncTest() {
        final List<TestBean> list = ListKit.of(new TestBean("张三", 12), new TestBean("李四", 13), new TestBean("王五", 12));
        final List<List<TestBean>> groupByField = CollKit.groupByFunc(list, TestBean::getAge);
        Assertions.assertEquals("张三", groupByField.get(0).get(0).getName());
        Assertions.assertEquals("王五", groupByField.get(0).get(1).getName());

        Assertions.assertEquals("李四", groupByField.get(1).get(0).getName());
    }

    @Test
    public void groupByFunc2Test() {
        final List<TestBean> list = ListKit.of(new TestBean("张三", 12), new TestBean("李四", 13), new TestBean("王五", 12));
        final List<List<TestBean>> groupByField = CollKit.groupByFunc(list, a -> a.getAge() > 12);
        Assertions.assertEquals("张三", groupByField.get(0).get(0).getName());
        Assertions.assertEquals("王五", groupByField.get(0).get(1).getName());

        Assertions.assertEquals("李四", groupByField.get(1).get(0).getName());
    }

    @Test
    public void sortByPropertyTest() {
        final List<TestBean> list = ListKit.of(
                new TestBean("张三", 12, DateKit.parse("2018-05-01")), //
                new TestBean("李四", 13, DateKit.parse("2018-03-01")), //
                new TestBean("王五", 12, DateKit.parse("2018-04-01"))//
        );

        CollKit.sortByProperty(list, "createTime");
        Assertions.assertEquals("李四", list.get(0).getName());
        Assertions.assertEquals("王五", list.get(1).getName());
        Assertions.assertEquals("张三", list.get(2).getName());
    }

    @Test
    public void sortByPropertyTest2() {
        final List<TestBean> list = ListKit.of(
                new TestBean("张三", 0, DateKit.parse("2018-05-01")), //
                new TestBean("李四", -12, DateKit.parse("2018-03-01")), //
                new TestBean("王五", 23, DateKit.parse("2018-04-01"))//
        );

        CollKit.sortByProperty(list, "age");
        Assertions.assertEquals("李四", list.get(0).getName());
        Assertions.assertEquals("张三", list.get(1).getName());
        Assertions.assertEquals("王五", list.get(2).getName());
    }

    @Test
    public void fieldValueMapTest() {
        final List<TestBean> list = ListKit.of(new TestBean("张三", 12, DateKit.parse("2018-05-01")), //
                new TestBean("李四", 13, DateKit.parse("2018-03-01")), //
                new TestBean("王五", 12, DateKit.parse("2018-04-01"))//
        );

        final Map<String, TestBean> map = CollKit.fieldValueMap(list, "name");
        Assertions.assertEquals("李四", map.get("李四").getName());
        Assertions.assertEquals("王五", map.get("王五").getName());
        Assertions.assertEquals("张三", map.get("张三").getName());
    }

    @Test
    public void fieldValueAsMapTest() {
        final List<TestBean> list = ListKit.of(new TestBean("张三", 12, DateKit.parse("2018-05-01")), //
                new TestBean("李四", 13, DateKit.parse("2018-03-01")), //
                new TestBean("王五", 14, DateKit.parse("2018-04-01"))//
        );

        final Map<String, Integer> map = CollKit.fieldValueAsMap(list, "name", "age");
        Assertions.assertEquals(Integer.valueOf(12), map.get("张三"));
        Assertions.assertEquals(Integer.valueOf(13), map.get("李四"));
        Assertions.assertEquals(Integer.valueOf(14), map.get("王五"));
    }

    @Test
    public void emptyTest() {
        final SortedSet<String> emptySortedSet = CollKit.empty(SortedSet.class);
        Assertions.assertEquals(Collections.emptySortedSet(), emptySortedSet);

        final Set<String> emptySet = CollKit.empty(Set.class);
        Assertions.assertEquals(Collections.emptySet(), emptySet);

        final List<String> emptyList = CollKit.empty(List.class);
        Assertions.assertEquals(Collections.emptyList(), emptyList);
    }

    @Test
    public void listTest() {
        final List<Object> list1 = ListKit.of(false);
        final List<Object> list2 = ListKit.of(true);

        Assertions.assertSame(ArrayList.class, list1);
        Assertions.assertSame(LinkedList.class, list2);
    }

    @Test
    public void listTest2() {
        final List<String> list1 = ListKit.of("a", "b", "c");
        final List<String> list2 = ListKit.ofLinked("a", "b", "c");
        Assertions.assertEquals("[a, b, c]", list1.toString());
        Assertions.assertEquals("[a, b, c]", list2.toString());
    }

    @Test
    public void listTest3() {
        final HashSet<String> set = new LinkedHashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");

        final List<String> list1 = ListKit.of(false, set);
        final List<String> list2 = ListKit.of(true, set);
        Assertions.assertEquals("[a, b, c]", list1.toString());
        Assertions.assertEquals("[a, b, c]", list2.toString());
    }

    @Test
    public void getTest() {
        final HashSet<String> set = SetKit.ofLinked("A", "B", "C", "D");
        String str = CollKit.get(set, 2);
        Assertions.assertEquals("C", str);

        str = CollKit.get(set, -1);
        Assertions.assertEquals("D", str);
    }

    @Test
    public void subInput1PositiveNegativePositiveOutput1() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 3;
        final int end = -1;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        arrayList.add(null);
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1ZeroPositivePositiveOutput1() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 0;
        final int end = 1;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);

        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        arrayList.add(null);
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1PositiveZeroOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 1;
        final int end = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end);

        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput0ZeroZeroZeroOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 0;
        final int step = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void subInput1PositiveNegativeZeroOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 1;
        final int end = -2_147_483_648;
        final int step = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1PositivePositivePositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 2_147_483_647;
        final int end = 2_147_483_647;
        final int step = 1_073_741_824;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1PositiveNegativePositiveOutputArrayIndexOutOfBoundsException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            // Arrange
            final List<Integer> list = new ArrayList<>();
            list.add(null);
            final int start = 2_147_483_643;
            final int end = -2_147_483_648;
            final int step = 2;

            // Act
            CollKit.sub(list, start, end, step);
            // Method is not expected to return due to exception thrown
        });
    }

    @Test
    public void subInput0ZeroPositiveNegativeOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 1;
        final int step = -2_147_483_646;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void subInput1PositivePositivePositiveOutput02() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(null);
        final int start = 2_147_483_643;
        final int end = 2_147_483_642;
        final int step = 1_073_741_824;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1ZeroZeroPositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(0);
        final int start = 0;
        final int end = 0;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput1NegativeZeroPositiveOutput0() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        list.add(0);
        final int start = -1;
        final int end = 0;
        final int step = 2;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end, step);
        // Assert result
        final List<Integer> arrayList = new ArrayList<>();
        Assertions.assertEquals(arrayList, retval);
    }

    @Test
    public void subInput0ZeroZeroOutputNull() {
        // Arrange
        final List<Integer> list = new ArrayList<>();
        final int start = 0;
        final int end = 0;
        // Act
        final List<Integer> retval = CollKit.sub(list, start, end);
        // Assert result
        Assertions.assertTrue(retval.isEmpty());
    }

    @Test
    public void containsAnyTest() {
        final List<Integer> list1 = ListKit.of(1, 2, 3, 4, 5);
        final List<Integer> list2 = ListKit.of(5, 3, 1, 9, 11);

        Assertions.assertTrue(CollKit.containsAny(list1, list2));
    }

    @Test
    public void containsAllTest() {
        final List<Integer> list1 = ListKit.of(1, 2, 3, 4, 5);
        final List<Integer> list2 = ListKit.of(5, 3, 1);
        Assertions.assertTrue(CollKit.containsAll(list1, list2));

        final List<Integer> list3 = ListKit.of(1);
        final List<Integer> list4 = ListKit.of();
        Assertions.assertTrue(CollKit.containsAll(list3, list4));
    }

    @Test
    public void getLastTest() {
        // 测试：空数组返回null而不是报错
        final List<String> test = ListKit.of();
        final String last = CollKit.getLast(test);
        Assertions.assertNull(last);
    }

    @Test
    public void zipTest() {
        final Collection<String> keys = ListKit.of("a", "b", "c", "d");
        final Collection<Integer> values = ListKit.of(1, 2, 3, 4);

        final Map<String, Integer> map = CollKit.zip(keys, values);

        Assertions.assertEquals(4, Objects.requireNonNull(map).size());

        Assertions.assertEquals(1, map.get("a").intValue());
        Assertions.assertEquals(2, map.get("b").intValue());
        Assertions.assertEquals(3, map.get("c").intValue());
        Assertions.assertEquals(4, map.get("d").intValue());
    }

    @Test
    public void toMapTest() {
        final Collection<String> keys = ListKit.of("a", "b", "c", "d");
        final Map<String, String> map = IteratorKit.toMap(keys, (value) -> "key" + value);
        Assertions.assertEquals("a", map.get("keya"));
        Assertions.assertEquals("b", map.get("keyb"));
        Assertions.assertEquals("c", map.get("keyc"));
        Assertions.assertEquals("d", map.get("keyd"));
    }

    @Test
    public void mapToMapTest() {
        final HashMap<String, String> oldMap = new HashMap<>();
        oldMap.put("a", "1");
        oldMap.put("b", "12");
        oldMap.put("c", "134");

        final Map<String, Long> map = IteratorKit.toMap(oldMap.entrySet(),
                Map.Entry::getKey,
                entry -> Long.parseLong(entry.getValue()));

        Assertions.assertEquals(1L, (long) map.get("a"));
        Assertions.assertEquals(12L, (long) map.get("b"));
        Assertions.assertEquals(134L, (long) map.get("c"));
    }

    @Test
    public void countMapTest() {
        final List<String> list = ListKit.of("a", "b", "c", "c", "a", "b", "d");
        final Map<String, Integer> countMap = CollKit.countMap(list);

        Assertions.assertEquals(Integer.valueOf(2), countMap.get("a"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("b"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("c"));
        Assertions.assertEquals(Integer.valueOf(1), countMap.get("d"));
    }

    @Test
    public void indexOfTest() {
        final List<String> list = ListKit.of("a", "b", "c", "c", "a", "b", "d");
        final int i = CollKit.indexOf(list, (str) -> str.charAt(0) == 'c');
        Assertions.assertEquals(2, i);
    }

    @Test
    public void lastIndexOfTest() {
        // List有优化
        final List<String> list = ListKit.of("a", "b", "c", "c", "a", "b", "d");
        final int i = CollKit.lastIndexOf(list, (str) -> str.charAt(0) == 'a');
        Assertions.assertEquals(4, i);

        final Queue<Integer> set = new ArrayDeque<>(Arrays.asList(1, 2, 3, 3, 2, 1));
        Assertions.assertEquals(5, CollKit.lastIndexOf(set, num -> num.equals(1)));
        Assertions.assertEquals(4, CollKit.lastIndexOf(set, num -> num.equals(2)));
        Assertions.assertEquals(3, CollKit.lastIndexOf(set, num -> num.equals(3)));
        Assertions.assertEquals(-1, CollKit.lastIndexOf(set, num -> num.equals(4)));
    }

    @Test
    public void lastIndexOfSetTest() {
        final Set<String> list = SetKit.ofLinked("a", "b", "c", "c", "a", "b", "d");
        // 去重后c排第三
        final int i = CollKit.lastIndexOf(list, (str) -> str.charAt(0) == 'c');
        Assertions.assertEquals(2, i);
    }

    @Test
    public void pageTest() {
        final List<Dictionary> objects = ListKit.of();
        for (int i = 0; i < 10; i++) {
            objects.add(Dictionary.of().set("name", "姓名：" + i));
        }

        Assertions.assertEquals(0, ListKit.page(5, 2, objects).size());
    }

    @Test
    public void subtractToListTest() {
        final List<Long> list1 = Arrays.asList(1L, 2L, 3L);
        final List<Long> list2 = Arrays.asList(2L, 3L);

        final List<Long> result = CollKit.subtractToList(list1, list2);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1L, (long) result.get(0));
    }

    @Test
    public void sortNaturalTest() {
        final List<String> of = ListKit.of("a", "c", "b");
        final List<String> sort = CollKit.sort(of, CompareKit.natural());
        Assertions.assertEquals("a,b,c", CollKit.join(sort, ","));
    }

    @Test
    public void setValueByMapTest() {
        final List<Person> people = Arrays.asList(
                new Person("aa", 12, "man", 1),
                new Person("bb", 13, "woman", 2),
                new Person("cc", 14, "man", 3),
                new Person("dd", 15, "woman", 4),
                new Person("ee", 16, "woman", 5),
                new Person("ff", 17, "man", 6)
        );

        final Map<Integer, String> genderMap = new HashMap<>();
        genderMap.put(1, null);
        genderMap.put(2, "妇女");
        genderMap.put(3, "少女");
        genderMap.put(4, "女");
        genderMap.put(5, "小孩");
        genderMap.put(6, "男");

        Assertions.assertEquals(people.get(1).getGender(), "woman");
        CollKit.setValueByMap(people, genderMap, Person::getId, Person::setGender);
        Assertions.assertEquals(people.get(1).getGender(), "妇女");

        final Map<Integer, Person> personMap = new HashMap<>();
        personMap.put(1, new Person("AA", 21, "男", 1));
        personMap.put(2, new Person("BB", 7, "小孩", 2));
        personMap.put(3, new Person("CC", 65, "老人", 3));
        personMap.put(4, new Person("DD", 35, "女人", 4));
        personMap.put(5, new Person("EE", 14, "少女", 5));
        personMap.put(6, null);

        CollKit.setValueByMap(people, personMap, Person::getId, (x, y) -> {
            x.setGender(y.getGender());
            x.setName(y.getName());
            x.setAge(y.getAge());
        });

        Assertions.assertEquals(people.get(1).getGender(), "小孩");
    }

    @Test
    public void distinctTest() {
        final List<Integer> distinct = CollKit.distinct(ListKit.view(5, 3, 10, 9, 0, 5, 10, 9));
        Assertions.assertEquals(ListKit.view(5, 3, 10, 9, 0), distinct);
    }

    @Test
    public void distinctByFunctionTest() {
        final List<Person> people = Arrays.asList(
                new Person("aa", 12, "man", 1),
                new Person("bb", 13, "woman", 2),
                new Person("cc", 14, "man", 3),
                new Person("dd", 15, "woman", 4),
                new Person("ee", 16, "woman", 5),
                new Person("ff", 17, "man", 6)
        );

        // 覆盖模式下ff覆盖了aa，ee覆盖了bb
        List<Person> distinct = CollKit.distinct(people, Person::getGender, true);
        Assertions.assertEquals(2, distinct.size());
        Assertions.assertEquals("ff", distinct.get(0).getName());
        Assertions.assertEquals("ee", distinct.get(1).getName());

        // 非覆盖模式下，保留了最早加入的aa和bb
        distinct = CollKit.distinct(people, Person::getGender, false);
        Assertions.assertEquals(2, distinct.size());
        Assertions.assertEquals("aa", distinct.get(0).getName());
        Assertions.assertEquals("bb", distinct.get(1).getName());
    }

    @Test
    public void mapTest() {
        final List<String> list = ListKit.of("a", "b", "c");
        final List<Object> extract = CollKit.map(list, (e) -> e + "_1");
        Assertions.assertEquals(ListKit.of("a_1", "b_1", "c_1"), extract);
    }

    @Test
    public void mapBeanTest() {
        final List<Person> people = Arrays.asList(
                new Person("aa", 12, "man", 1),
                new Person("bb", 13, "woman", 2),
                new Person("cc", 14, "man", 3),
                new Person("dd", 15, "woman", 4)
        );

        final List<Object> extract = CollKit.map(people, Person::getName);
        Assertions.assertEquals(ListKit.of("aa", "bb", "cc", "dd"), extract);
    }

    @Test
    public void createTest() {
        final Collection<Object> collection = CollKit.create(Collections.emptyList().getClass());
        Console.log(collection.getClass());
        Assertions.assertNotNull(collection);
    }

    @Test
    public void transTest() {
        final List<Person> people = Arrays.asList(
                new Person("aa", 12, "man", 1),
                new Person("bb", 13, "woman", 2),
                new Person("cc", 14, "man", 3),
                new Person("dd", 15, "woman", 4)
        );

        final Collection<String> trans = CollKit.trans(people, Person::getName);
        Assertions.assertEquals("[aa, bb, cc, dd]", trans.toString());
    }

    @Test
    public void unionNullTest() {
        final List<String> list1 = new ArrayList<>();
        final List<String> list2 = null;
        final List<String> list3 = null;
        final Collection<String> union = CollKit.union(list1, list2, list3);
        Assertions.assertNotNull(union);
    }

    @Test
    public void unionDistinctNullTest() {
        final List<String> list1 = new ArrayList<>();
        final List<String> list2 = null;
        final List<String> list3 = null;
        final Set<String> set = CollKit.unionDistinct(list1, list2, list3);
        Assertions.assertNotNull(set);
    }

    @Test
    public void unionAllNullTest() {
        final List<String> list1 = new ArrayList<>();
        final List<String> list2 = null;
        final List<String> list3 = null;
        final List<String> list = CollKit.unionAll(list1, list2, list3);
        Assertions.assertNotNull(list);

        Assertions.assertEquals(
                ListKit.of(1, 2, 3, 4),
                CollKit.unionAll(ListKit.of(1), ListKit.of(2), ListKit.of(3), ListKit.of(4))
        );
    }

    @Test
    public void intersectionNullTest() {
        final List<String> list1 = new ArrayList<>();
        list1.add("aa");
        final List<String> list2 = new ArrayList<>();
        list2.add("aa");
        final List<String> list3 = null;
        final Collection<String> collection = CollKit.intersection(list1, list2, list3);
        Assertions.assertNotNull(collection);
    }

    @Test
    public void intersectionDistinctNullTest() {
        final List<String> list1 = new ArrayList<>();
        list1.add("aa");
        final List<String> list2 = null;
        // list2.add("aa");
        final List<String> list3 = null;
        final Collection<String> collection = CollKit.intersectionDistinct(list1, list2, list3);
        Assertions.assertNotNull(collection);
    }

    @Test
    public void addIfAbsentTest() {
        // 为false的情况
        Assertions.assertFalse(CollKit.addIfAbsent(null, null));
        Assertions.assertFalse(CollKit.addIfAbsent(ListKit.of(), null));
        Assertions.assertFalse(CollKit.addIfAbsent(null, "123"));
        Assertions.assertFalse(CollKit.addIfAbsent(ListKit.of("123"), "123"));
        Assertions.assertFalse(CollKit.addIfAbsent(ListKit.of(new Animal("jack", 20)),
                new Animal("jack", 20)));

        // 正常情况
        Assertions.assertTrue(CollKit.addIfAbsent(ListKit.of("456"), "123"));
        Assertions.assertTrue(CollKit.addIfAbsent(ListKit.of(new Animal("jack", 20)),
                new Dog("jack", 20)));
        Assertions.assertTrue(CollKit.addIfAbsent(ListKit.of(new Animal("jack", 20)),
                new Animal("tom", 20)));
    }

    @Test
    public void getFirstTest() {
        Assertions.assertNull(CollKit.getFirst(null));
        Assertions.assertNull(CollKit.getFirst(ListKit.of()));

        Assertions.assertEquals("1", CollKit.getFirst(ListKit.of("1", "2", "3")));
        final ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("3");
        deque.add("4");
        Assertions.assertEquals("3", CollKit.getFirst(deque));
    }

    @Test
    public void popPartTest() {
        final Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        final List<Integer> popPart1 = CollKit.popPart(stack, 3);
        Assertions.assertEquals(ListKit.of(9, 8, 7), popPart1);
        Assertions.assertEquals(7, stack.size());

        final ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }
        final List<Integer> popPart2 = CollKit.popPart(queue, 3);
        Assertions.assertEquals(ListKit.of(9, 8, 7), popPart2);
        Assertions.assertEquals(7, queue.size());
    }

    @Test
    public void isEqualListTest() {
        final List<Integer> list = ListKit.of(1, 2, 3, 4);
        Assertions.assertTrue(CollKit.isEqualList(null, null));
        Assertions.assertTrue(CollKit.isEqualList(ListKit.of(), ListKit.of()));
        Assertions.assertTrue(CollKit.isEqualList(list, list));
        Assertions.assertTrue(CollKit.isEqualList(list, ListKit.of(1, 2, 3, 4)));

        Assertions.assertFalse(CollKit.isEqualList(null, ListKit.of()));
        Assertions.assertFalse(CollKit.isEqualList(list, ListKit.of(1, 2, 3, 3)));
        Assertions.assertFalse(CollKit.isEqualList(list, ListKit.of(1, 2, 3)));
        Assertions.assertFalse(CollKit.isEqualList(list, ListKit.of(4, 3, 2, 1)));
    }

    @Test
    public void testMatch() {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Assertions.assertTrue(CollKit.anyMatch(list, i -> i == 1));
        Assertions.assertFalse(CollKit.anyMatch(list, i -> i > 6));
        Assertions.assertFalse(CollKit.allMatch(list, i -> i == 1));
        Assertions.assertTrue(CollKit.allMatch(list, i -> i <= 6));
    }

    @Test
    public void maxTest() {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        Assertions.assertEquals((Integer) 6, CollKit.max(list));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void maxEmptyTest() {
        final List<? extends Comparable> emptyList = Collections.emptyList();
        Assertions.assertNull(CollKit.max(emptyList));
    }

    @Test
    public void minNullTest() {
        Assertions.assertNull(CollKit.max(null));
    }

    @Test
    public void unionExtendTest() {
        final List<Dog> dog = Arrays.asList(new Dog("dog1", 12), new Dog("dog2", 12));
        final List<Cat> cat = Arrays.asList(new Cat("cat1", 12), new Cat("cat2", 12));
        Assertions.assertEquals(CollKit.union(dog, cat).size(), dog.size() + cat.size());
    }

    @Test
    public void unionAllExtendTest() {
        final List<Dog> dog = Arrays.asList(new Dog("dog1", 12), new Dog("dog2", 12));
        final List<Cat> cat = Arrays.asList(new Cat("cat1", 12), new Cat("cat2", 12));
        final List<Pig> pig = Arrays.asList(new Pig("pig1", 12), new Pig("pig2", 12));
        Assertions.assertEquals(CollKit.unionAll(dog, cat, pig).size(), dog.size() + cat.size() + pig.size());
    }

    @Test
    public void unionDistinctExtendTest() {
        final List<Dog> dog = Arrays.asList(new Dog("dog1", 12), new Dog("dog1", 12)); // same
        final List<Cat> cat = Arrays.asList(new Cat("cat1", 12), new Cat("cat2", 12));
        final List<Pig> pig = Arrays.asList(new Pig("pig1", 12), new Pig("pig2", 12));
        Assertions.assertEquals(CollKit.unionDistinct(dog, cat, pig).size(), 5);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void flatListTest1() {
        final List<List<List<String>>> list = Arrays.asList(Arrays.asList(Arrays.asList("1", "2", "3"), Arrays.asList("5", "6", "7")));

        final List<Object> objects = CollKit.flat(list);

        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "5", "6", "7"}, objects.toArray());
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void flatListTest2() {
        final List<List<List<String>>> list = Arrays.asList(
                Arrays.asList(
                        Arrays.asList("a"),
                        Arrays.asList("b", "c"),
                        Arrays.asList("d", "e", "f")
                ),
                Arrays.asList(
                        Arrays.asList("g", "h", "i"),
                        Arrays.asList("j", "k", "l")
                )
        );
        final List<Object> flat = CollKit.flat(list);
        Assertions.assertArrayEquals(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"}, flat.toArray());

    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    void flatListTest3() {
        final List<List<List<String>>> list = Arrays.asList(
                Arrays.asList(
                        Arrays.asList("a"),
                        Arrays.asList("b", "c", null),
                        Arrays.asList("d", "e", "f")
                ),
                Arrays.asList(
                        Arrays.asList("g", "h", "i"),
                        Arrays.asList("j", "k", "l")
                )
        );
        final List<Object> flat = CollKit.flat(list, false);
        Assertions.assertArrayEquals(new String[]{"a", "b", "c", null, "d", "e", "f", "g", "h", "i", "j", "k", "l"}, flat.toArray());
    }

    @Test
    void issueTest() {
        final ArrayList<String> coll1 = new ArrayList<>();
        coll1.add("1");
        coll1.add("2");
        coll1.add("3");
        coll1.add("4");
        final ArrayList<String> coll2 = new ArrayList<>();
        coll2.add("1");
        coll2.add("1");
        coll2.add("1");
        coll2.add("1");
        coll2.add("1");

        Assertions.assertTrue(CollKit.containsAll(coll1, coll2));
    }

    @Test
    public void testToIdentityMap() {
        Map<Long, Student> map = CollKit.toIdentityMap(null, Student::getStudentId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        final List<Student> list = new ArrayList<>();
        map = CollKit.toIdentityMap(list, Student::getStudentId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 1, 2, "李四"));
        list.add(new Student(1, 1, 3, "王五"));
        map = CollKit.toIdentityMap(list, Student::getStudentId);
        Assertions.assertEquals(map.get(1L).getName(), "张三");
        Assertions.assertEquals(map.get(2L).getName(), "李四");
        Assertions.assertEquals(map.get(3L).getName(), "王五");
        Assertions.assertNull(map.get(4L));

        // 测试value为空时
        list.add(null);
        map = CollKit.toIdentityMap(list, Student::getStudentId);
        Assertions.assertNull(map.get(4L));
    }

    @Test
    public void testToMap() {
        Map<Long, String> map = CollKit.toMap(null, Student::getStudentId, Student::getName);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        final List<Student> list = new ArrayList<>();
        map = CollKit.toMap(list, Student::getStudentId, Student::getName);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 1, 2, "李四"));
        list.add(new Student(1, 1, 3, "王五"));
        map = CollKit.toMap(list, Student::getStudentId, Student::getName);
        Assertions.assertEquals(map.get(1L), "张三");
        Assertions.assertEquals(map.get(2L), "李四");
        Assertions.assertEquals(map.get(3L), "王五");
        Assertions.assertNull(map.get(4L));

        // 测试value为空时
        list.add(new Student(1, 1, 4, null));
        map = CollKit.toMap(list, Student::getStudentId, Student::getName);
        Assertions.assertNull(map.get(4L));
    }

    @Test
    public void testGroupByKey() {
        Map<Long, List<Student>> map = CollKit.groupByKey(null, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        final List<Student> list = new ArrayList<>();
        map = CollKit.groupByKey(list, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 2, "李四"));
        list.add(new Student(2, 1, 1, "擎天柱"));
        list.add(new Student(2, 2, 2, "威震天"));
        list.add(new Student(2, 3, 2, "霸天虎"));
        map = CollKit.groupByKey(list, Student::getClassId);
        final Map<Long, List<Student>> compare = new HashMap<>();
        final List<Student> class1 = new ArrayList<>();
        class1.add(new Student(1, 1, 1, "张三"));
        class1.add(new Student(2, 1, 1, "擎天柱"));
        compare.put(1L, class1);
        final List<Student> class2 = new ArrayList<>();
        class2.add(new Student(1, 2, 2, "李四"));
        class2.add(new Student(2, 2, 2, "威震天"));

        compare.put(2L, class2);
        final List<Student> class3 = new ArrayList<>();
        class3.add(new Student(2, 3, 2, "霸天虎"));
        compare.put(3L, class3);
        Assertions.assertEquals(map, compare);
    }

    @Test
    public void testGroupBy2Key() {
        Map<Long, Map<Long, List<Student>>> map = CollKit.groupBy2Key(null, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        final List<Student> list = new ArrayList<>();
        map = CollKit.groupBy2Key(list, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 2, "李四"));
        list.add(new Student(1, 2, 3, "王五"));
        list.add(new Student(2, 1, 1, "擎天柱"));
        list.add(new Student(2, 2, 2, "威震天"));
        list.add(new Student(2, 2, 3, "霸天虎"));
        map = CollKit.groupBy2Key(list, Student::getTermId, Student::getClassId);
        final Map<Long, Map<Long, List<Student>>> compare = new HashMap<>();
        final Map<Long, List<Student>> map1 = new HashMap<>();
        final List<Student> list11 = new ArrayList<>();
        list11.add(new Student(1, 1, 1, "张三"));
        map1.put(1L, list11);
        compare.put(1L, map1);
        final List<Student> list12 = new ArrayList<>();
        list12.add(new Student(1, 2, 2, "李四"));
        list12.add(new Student(1, 2, 3, "王五"));
        map1.put(2L, list12);
        compare.put(2L, map1);
        final Map<Long, List<Student>> map2 = new HashMap<>();
        final List<Student> list21 = new ArrayList<>();
        list21.add(new Student(2, 1, 1, "擎天柱"));
        map2.put(1L, list21);
        compare.put(2L, map2);

        final List<Student> list22 = new ArrayList<>();
        list22.add(new Student(2, 2, 2, "威震天"));
        list22.add(new Student(2, 2, 3, "霸天虎"));
        map2.put(2L, list22);
        compare.put(2L, map2);
        Assertions.assertEquals(map, compare);
    }

    @Test
    public void testGroup2Map() {
        Map<Long, Map<Long, Student>> map = CollKit.group2Map(null, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);

        final List<Student> list = new ArrayList<>();
        map = CollKit.group2Map(list, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 1, "李四"));
        list.add(new Student(2, 2, 1, "王五"));
        map = CollKit.group2Map(list, Student::getTermId, Student::getClassId);
        final Map<Long, Map<Long, Student>> compare = new HashMap<>();
        final Map<Long, Student> map1 = new HashMap<>();
        map1.put(1L, new Student(1, 1, 1, "张三"));
        map1.put(2L, new Student(1, 2, 1, "李四"));
        compare.put(1L, map1);
        final Map<Long, Student> map2 = new HashMap<>();
        map2.put(2L, new Student(2, 2, 1, "王五"));
        compare.put(2L, map2);
        Assertions.assertEquals(compare, map);

        // 对null友好
        final Map<Long, Map<Long, Student>> termIdClassIdStudentMap = CollKit.group2Map(Arrays.asList(null, new Student(2, 2, 1, "王五")), Student::getTermId, Student::getClassId);
        final Map<Long, Map<Long, Student>> termIdClassIdStudentCompareMap = new HashMap<>() {
            private static final long serialVersionUID = 1L;

            {
                put(null, MapKit.empty());
                put(2L, MapKit.of(2L, new Student(2, 2, 1, "王五")));
            }
        };
        Assertions.assertEquals(termIdClassIdStudentCompareMap, termIdClassIdStudentMap);
    }

    @Test
    public void testGroupKeyValue() {
        Map<Long, List<Long>> map = CollKit.groupKeyValue(null, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);

        final List<Student> list = new ArrayList<>();
        map = CollKit.groupKeyValue(list, Student::getTermId, Student::getClassId);
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 1, "李四"));
        list.add(new Student(2, 2, 1, "王五"));
        map = CollKit.groupKeyValue(list, Student::getTermId, Student::getClassId);

        final Map<Long, List<Long>> compare = new HashMap<>();
        compare.put(1L, Arrays.asList(1L, 2L));
        compare.put(2L, Collections.singletonList(2L));
        Assertions.assertEquals(compare, map);
    }

    @Test
    public void testGroupBy() {
        // groupBy作为之前所有group函数的公共部分抽取出来，并更接近于jdk原生，灵活性更强

        // 参数null测试
        Map<Long, List<Student>> map = CollKit.groupBy(null, Student::getTermId, Collectors.toList());
        Assertions.assertEquals(map, Collections.EMPTY_MAP);

        // 参数空数组测试
        final List<Student> list = new ArrayList<>();
        map = CollKit.groupBy(list, Student::getTermId, Collectors.toList());
        Assertions.assertEquals(map, Collections.EMPTY_MAP);

        // 放入元素
        list.add(new Student(1, 1, 1, "张三"));
        list.add(new Student(1, 2, 1, "李四"));
        list.add(new Student(2, 2, 1, "王五"));
        // 先根据termId分组，再通过classId比较，找出最大值所属的那个Student,返回的Optional
        final Map<Long, Optional<Student>> longOptionalMap = CollKit.groupBy(list, Student::getTermId, Collectors.maxBy(Comparator.comparing(Student::getClassId)));
        //noinspection OptionalGetWithoutIsPresent
        Assertions.assertEquals("李四", longOptionalMap.get(1L).get().getName());

        // 先根据termId分组，再转换为Map<studentId,name>
        final Map<Long, HashMap<Long, String>> groupThen = CollKit.groupBy(list, Student::getTermId, Collector.of(HashMap::new, (m, v) -> m.put(v.getStudentId(), v.getName()), (l, r) -> l));
        Assertions.assertEquals(
                MapKit.builder()
                        .put(1L, MapKit.builder().put(1L, "李四").build())
                        .put(2L, MapKit.builder().put(1L, "王五").build())
                        .build(),
                groupThen);

        // 总之，如果你是想要group分组后还要进行别的操作，用它就对了！
        // 并且对null值进行了友好处理，例如
        final List<Student> students = Arrays.asList(null, null, new Student(1, 1, 1, "张三"),
                new Student(1, 2, 1, "李四"));
        final Map<Long, List<Student>> termIdStudentsMap = CollKit.groupBy(students, Student::getTermId, Collectors.toList());
        final Map<Long, List<Student>> termIdStudentsCompareMap = new HashMap<>();
        termIdStudentsCompareMap.put(null, Collections.emptyList());
        termIdStudentsCompareMap.put(1L, Arrays.asList(new Student(1L, 1, 1, "张三"), new Student(1L, 2, 1, "李四")));
        Assertions.assertEquals(termIdStudentsCompareMap, termIdStudentsMap);

        final Map<Long, Long> termIdCountMap = CollKit.groupBy(students, Student::getTermId, Collectors.counting());
        final Map<Long, Long> termIdCountCompareMap = new HashMap<>();
        termIdCountCompareMap.put(null, 2L);
        termIdCountCompareMap.put(1L, 2L);
        Assertions.assertEquals(termIdCountCompareMap, termIdCountMap);
    }


    @Test
    public void testTranslate2List() {
        List<String> list = CollKit.toList(null, Student::getName);
        Assertions.assertEquals(list, Collections.EMPTY_LIST);
        final List<Student> students = new ArrayList<>();
        list = CollKit.toList(students, Student::getName);
        Assertions.assertEquals(list, Collections.EMPTY_LIST);
        students.add(new Student(1, 1, 1, "张三"));
        students.add(new Student(1, 2, 2, "李四"));
        students.add(new Student(2, 1, 1, "李四"));
        students.add(new Student(2, 2, 2, "李四"));
        students.add(new Student(2, 3, 2, "霸天虎"));
        list = CollKit.toList(students, Student::getName);
        final List<String> compare = new ArrayList<>();
        compare.add("张三");
        compare.add("李四");
        compare.add("李四");
        compare.add("李四");
        compare.add("霸天虎");
        Assertions.assertEquals(list, compare);
    }

    @Test
    public void testTranslate2Set() {
        Set<String> set = CollKit.toSet(null, Student::getName);
        Assertions.assertEquals(set, Collections.EMPTY_SET);
        final List<Student> students = new ArrayList<>();
        set = CollKit.toSet(students, Student::getName);
        Assertions.assertEquals(set, Collections.EMPTY_SET);
        students.add(new Student(1, 1, 1, "张三"));
        students.add(new Student(1, 2, 2, "李四"));
        students.add(new Student(2, 1, 1, "李四"));
        students.add(new Student(2, 2, 2, "李四"));
        students.add(new Student(2, 3, 2, "霸天虎"));
        set = CollKit.toSet(students, Student::getName);
        final Set<String> compare = new HashSet<>();
        compare.add("张三");
        compare.add("李四");
        compare.add("霸天虎");
        Assertions.assertEquals(set, compare);
    }


    @Test
    public void testMerge() {
        Map<Long, Student> map1 = null;
        Map<Long, Student> map2 = Collections.emptyMap();
        Map<Long, String> map = CollKit.merge(map1, map2, (s1, s2) -> s1.getName() + s2.getName());
        Assertions.assertEquals(map, Collections.EMPTY_MAP);
        map1 = new HashMap<>();
        map1.put(1L, new Student(1, 1, 1, "张三"));
        map = CollKit.merge(map1, map2, this::merge);
        final Map<Long, String> temp = new HashMap<>();
        temp.put(1L, "张三");
        Assertions.assertEquals(map, temp);
        map2 = new HashMap<>();
        map2.put(1L, new Student(2, 1, 1, "李四"));
        map = CollKit.merge(map1, map2, this::merge);
        final Map<Long, String> compare = new HashMap<>();
        compare.put(1L, "张三李四");
        Assertions.assertEquals(map, compare);
    }

    private String merge(final Student student1, final Student student2) {
        if (student1 == null && student2 == null) {
            return null;
        } else if (student1 == null) {
            return student2.getName();
        } else if (student2 == null) {
            return student1.getName();
        } else {
            return student1.getName() + student2.getName();
        }
    }

    /**
     * 观察输出的打印为不重复的
     */
    @Test
    public void ringNextIntByObjTest() {
        final AtomicInteger atomicInteger = new AtomicInteger();
        // 开启并发测试，每个线程获取到的元素都是唯一的
        ThreadKit.concurrencyTest(strList.size(), () -> {
            final int index = CollKit.ringNextIntByObject(strList, atomicInteger);
            final String s = strList.get(index);
            Assertions.assertNotNull(s);
        });
    }

    @Data
    @AllArgsConstructor
    public static class TestBean {
        private String name;
        private int age;
        private Date createTime;

        public TestBean(final String name, final int age) {
            this.name = name;
            this.age = age;
        }
    }

    /**
     * 班级类
     */
    @Data
    @AllArgsConstructor
    @ToString
    public static class Student {
        private long termId;//学期id
        private long classId;//班级id
        private long studentId;//班级id
        private String name;//学生名称
    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;
        private Integer age;
        private String gender;
        private Integer id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Animal {
        private String name;
        private Integer age;
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Data
    static class Dog extends Animal {
        public Dog(final String name, final Integer age) {
            super(name, age);
        }
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Data
    static class Cat extends Animal {

        public Cat(final String name, final Integer age) {
            super(name, age);
        }
    }

    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Data
    static class Pig extends Animal {

        public Pig(final String name, final Integer age) {
            super(name, age);
        }
    }

}
