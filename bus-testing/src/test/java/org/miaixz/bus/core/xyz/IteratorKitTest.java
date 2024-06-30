package org.miaixz.bus.core.xyz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.iterator.FilterIterator;
import org.miaixz.bus.core.center.iterator.IteratorEnumeration;
import org.w3c.dom.NodeList;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test for {@link IteratorKit}
 */
public class IteratorKitTest {

    @Test
    public void testGetIter() {
        Assertions.assertNull(IteratorKit.getIter(null));
        assertEquals(Collections.emptyIterator(), IteratorKit.getIter(Collections.emptyList()));

        Assertions.assertNull(IteratorKit.getIter((Object) null));
        Assertions.assertNotNull(IteratorKit.getIter(Collections.emptyIterator()));
        Assertions.assertNotNull(IteratorKit.getIter((Object) Collections.emptyList()));
        Assertions.assertNotNull(IteratorKit.getIter(new Integer[0]));
        Assertions.assertNotNull(IteratorKit.getIter(Collections.emptyMap()));
        Assertions.assertNull(IteratorKit.getIter((NodeList) null));
    }

    @Test
    public void testIsEmpty() {
        Assertions.assertTrue(IteratorKit.isEmpty(Collections.emptyIterator()));
        Assertions.assertFalse(IteratorKit.isEmpty(Arrays.asList(1, 2).iterator()));

        Assertions.assertTrue(IteratorKit.isEmpty(Collections.emptyList()));
        Assertions.assertFalse(IteratorKit.isEmpty(Arrays.asList(1, 2)));
    }

    @Test
    public void testIsNotEmpty() {
        Assertions.assertFalse(IteratorKit.isNotEmpty(Collections.emptyIterator()));
        Assertions.assertTrue(IteratorKit.isNotEmpty(Arrays.asList(1, 2).iterator()));

        Assertions.assertFalse(IteratorKit.isNotEmpty(Collections.emptyList()));
        Assertions.assertTrue(IteratorKit.isNotEmpty(Arrays.asList(1, 2)));
    }

    @Test
    public void testHasNull() {
        Assertions.assertFalse(IteratorKit.hasNull(Arrays.asList(1, 3, 2).iterator()));
        Assertions.assertTrue(IteratorKit.hasNull(Arrays.asList(1, null, 2).iterator()));
        Assertions.assertFalse(IteratorKit.hasNull(Collections.emptyIterator()));
        Assertions.assertTrue(IteratorKit.hasNull(null));
    }

    @Test
    public void testIsAllNull() {
        Assertions.assertTrue(IteratorKit.isAllNull(Arrays.asList(null, null)));
        Assertions.assertFalse(IteratorKit.isAllNull(Arrays.asList(1, null)));
        Assertions.assertTrue(IteratorKit.isAllNull((Iterable<?>) null));
        Assertions.assertTrue(IteratorKit.isAllNull(Arrays.asList(null, null).iterator()));
        Assertions.assertFalse(IteratorKit.isAllNull(Arrays.asList(1, null).iterator()));
        Assertions.assertTrue(IteratorKit.isAllNull((Iterator<?>) null));
    }

    @Test
    public void testCountMap() {
        final Object o1 = new Object();
        final Object o2 = new Object();
        final Map<Object, Integer> countMap = IteratorKit.countMap(Arrays.asList(o1, o2, o1, o1).iterator());
        Assertions.assertEquals((Integer) 3, countMap.get(o1));
        Assertions.assertEquals((Integer) 1, countMap.get(o2));
    }

    @Test
    public void testFieldValueMap() {
        final Bean bean1 = new Bean(1, "A");
        final Bean bean2 = new Bean(2, "B");
        final Map<Integer, Bean> map = IteratorKit.fieldValueMap(Arrays.asList(bean1, bean2).iterator(), "id");
        Assertions.assertEquals(bean1, map.get(1));
        Assertions.assertEquals(bean2, map.get(2));
    }

    @Test
    public void testFieldValueAsMap() {
        final Bean bean1 = new Bean(1, "A");
        final Bean bean2 = new Bean(2, "B");
        final Map<Integer, String> map = IteratorKit.fieldValueAsMap(
                Arrays.asList(bean1, bean2).iterator(), "id", "name"
        );
        Assertions.assertEquals("A", map.get(1));
        Assertions.assertEquals("B", map.get(2));
    }

    @Test
    public void testFieldValueList() {
        final Bean bean1 = new Bean(1, "A");
        final Bean bean2 = new Bean(2, "B");
        Assertions.assertEquals(Arrays.asList(1, 2), IteratorKit.fieldValueList(Arrays.asList(bean1, bean2), "id"));
        Assertions.assertEquals(
                Arrays.asList(1, 2),
                IteratorKit.fieldValueList(Arrays.asList(bean1, bean2).iterator(), "id")
        );
    }

    @Test
    public void testJoin() {
        final List<String> stringList = Arrays.asList("1", "2", "3");
        Assertions.assertEquals("123", IteratorKit.join(stringList.iterator(), ""));
        Assertions.assertEquals("-1--2--3-", IteratorKit.join(stringList.iterator(), "", "-", "-"));
        Assertions.assertEquals("123", IteratorKit.join(stringList.iterator(), "", Function.identity()));
    }

    @Test
    public void testToMap() {
        final List<Integer> keys = Arrays.asList(1, 2, 3);

        Map<Integer, Integer> map = IteratorKit.toMap(keys, keys);
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));

        map = IteratorKit.toMap(keys.iterator(), keys.iterator());
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));

        map = IteratorKit.toMap(keys.iterator(), keys.iterator(), true);
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));

        map = IteratorKit.toMap(keys, keys, true);
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));

        map = IteratorKit.toMap(keys, Function.identity());
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));

        map = IteratorKit.toMap(keys, Function.identity(), Function.identity());
        Assertions.assertEquals(keys, new ArrayList<>(map.keySet()));
        Assertions.assertEquals(keys, new ArrayList<>(map.values()));
    }

    @Test
    public void testToListMap() {
        final List<Integer> keys = Arrays.asList(1, 2, 3, 4);

        Map<Boolean, List<Integer>> map = IteratorKit.toListMap(keys, i -> (i & 1) == 0, Function.identity());
        Assertions.assertEquals(Arrays.asList(2, 4), map.get(true));
        Assertions.assertEquals(Arrays.asList(1, 3), map.get(false));

        map = IteratorKit.toListMap(keys, i -> (i & 1) == 0);
        Assertions.assertEquals(Arrays.asList(2, 4), map.get(true));
        Assertions.assertEquals(Arrays.asList(1, 3), map.get(false));

        map = new LinkedHashMap<>();
        final Map<Boolean, List<Integer>> rawMap = IteratorKit.toListMap(map, keys, i -> (i & 1) == 0, Function.identity());
        Assertions.assertSame(rawMap, map);
        Assertions.assertEquals(Arrays.asList(2, 4), rawMap.get(true));
        Assertions.assertEquals(Arrays.asList(1, 3), rawMap.get(false));
    }

    @Test
    public void testToListMap2() {
        final Map<String, List<String>> expectedMap = new HashMap<>();
        expectedMap.put("a", Collections.singletonList("and"));
        expectedMap.put("b", Arrays.asList("brave", "back"));

        final Map<String, List<String>> testMap = IteratorKit.toListMap(Arrays.asList("and", "brave", "back"),
                v -> v.substring(0, 1));
        Assertions.assertEquals(testMap, expectedMap);
    }

    @Test
    public void testAsIterable() {
        final Iterator<Integer> iter = Arrays.asList(1, 2, 3).iterator();
        Assertions.assertEquals(iter, IteratorKit.asIterable(iter).iterator());
        Assertions.assertNull(IteratorKit.asIterable(null).iterator());

        final Enumeration<Integer> enumeration = new IteratorEnumeration<>(iter);
        final Iterator<Integer> iter2 = IteratorKit.asIterator(enumeration);
        Assertions.assertEquals((Integer) 1, iter2.next());
        Assertions.assertEquals((Integer) 2, iter2.next());
        Assertions.assertEquals((Integer) 3, iter2.next());
    }

    @Test
    public void testGet() {
        final Iterator<Integer> iter = Arrays.asList(1, 2, 3, 4).iterator();
        Assertions.assertEquals((Integer) 3, IteratorKit.get(iter, 2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> IteratorKit.get(iter, -1));
    }

    @Test
    public void testGetFirst() {
        final Iterator<Integer> iter = Arrays.asList(1, 2, 3, 4).iterator();
        Assertions.assertEquals((Integer) 1, IteratorKit.getFirst(iter));
        Assertions.assertNull(IteratorKit.getFirst(null));
        Assertions.assertNull(IteratorKit.getFirst(Collections.emptyIterator()));

        Assertions.assertEquals((Integer) 2, IteratorKit.getFirst(iter, t -> (t & 1) == 0));
        Assertions.assertNull(IteratorKit.getFirst((Iterator<Integer>) null, t -> (t & 1) == 0));
        Assertions.assertNull(IteratorKit.getFirst(Collections.emptyIterator(), Objects::nonNull));
    }

    @Test
    public void testGetFirstNoneNull() {
        final Iterator<Integer> iter = Arrays.asList(null, 2, null, 4).iterator();
        Assertions.assertEquals((Integer) 2, IteratorKit.getFirstNoneNull(iter));
        Assertions.assertNull(IteratorKit.getFirstNoneNull(null));
        Assertions.assertNull(IteratorKit.getFirstNoneNull(Collections.emptyIterator()));
    }

    @Test
    public void testGetElementType() {
        final List<Object> list = Arrays.asList(null, "str", null);
        Assertions.assertEquals(String.class, IteratorKit.getElementType(list));
        Assertions.assertNull(IteratorKit.getElementType((Iterable<?>) null));
        Assertions.assertNull(IteratorKit.getElementType(Collections.emptyList()));

        Assertions.assertEquals(String.class, IteratorKit.getElementType(list.iterator()));
        Assertions.assertNull(IteratorKit.getElementType((Iterator<?>) null));
        Assertions.assertNull(IteratorKit.getElementType(Collections.emptyIterator()));
    }

    @Test
    public void testEdit() {
        Assertions.assertEquals(
                Collections.singletonList("str"),
                IteratorKit.edit(Arrays.asList(null, "str", null).iterator(), t -> t)
        );
        Assertions.assertEquals(
                Collections.singletonList("str"),
                IteratorKit.edit(Arrays.asList(null, "str", null).iterator(), null)
        );
        Assertions.assertEquals(Collections.emptyList(), IteratorKit.edit(null, t -> t));
    }

    @Test
    public void testRemove() {
        final List<Integer> list = new ArrayList<>(Arrays.asList(1, null, null, 3));
        IteratorKit.remove(list.iterator(), Objects::isNull);
        Assertions.assertEquals(Arrays.asList(1, 3), list);
    }

    @Test
    public void testFilterToList() {
        final List<Integer> list1 = new ArrayList<>(Arrays.asList(1, null, null, 3));
        final List<Integer> list2 = IteratorKit.filterToList(list1.iterator(), Objects::nonNull);
        Assertions.assertEquals(Arrays.asList(1, 3), list2);
    }

    @Test
    public void testFiltered() {
        Assertions.assertNotNull(IteratorKit.filtered(Collections.emptyIterator(), t -> true));
    }

    @Test
    public void testEmpty() {
        Assertions.assertSame(Collections.emptyIterator(), IteratorKit.empty());
    }

    @Test
    public void testTrans() {
        Assertions.assertNotNull(IteratorKit.trans(Collections.emptyIterator(), t -> true));
    }

    @Test
    public void testSize() {
        Assertions.assertEquals(0, IteratorKit.size((Iterator<?>) null));
        Assertions.assertEquals(0, IteratorKit.size(Collections.emptyIterator()));
        Assertions.assertEquals(3, IteratorKit.size(Arrays.asList(1, 2, 3).iterator()));

        Assertions.assertEquals(0, IteratorKit.size((Iterable<?>) null));
        Assertions.assertEquals(0, IteratorKit.size(Collections.emptyList()));
        Assertions.assertEquals(3, IteratorKit.size(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void testIsEqualList() {
        Assertions.assertFalse(IteratorKit.isEqualList(null, Collections.emptyList()));
        Assertions.assertFalse(IteratorKit.isEqualList(Arrays.asList(1, 2, 3), Collections.emptyList()));
        Assertions.assertFalse(IteratorKit.isEqualList(Arrays.asList(1, 2, 3), Arrays.asList(1, 2)));
        Assertions.assertTrue(IteratorKit.isEqualList(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3)));
        Assertions.assertTrue(IteratorKit.isEqualList(null, null));
        Assertions.assertTrue(IteratorKit.isEqualList(Collections.emptyList(), Collections.emptyList()));
    }

    @Test
    public void testClear() {
        final List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        IteratorKit.clear(list.iterator());
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertThrows(UnsupportedOperationException.class, () -> IteratorKit.clear(Arrays.asList(1, 2).iterator()));
    }

    @Test
    public void testToStr() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        Assertions.assertEquals("[1, 2, 3]", IteratorKit.toString(list.iterator()));
        Assertions.assertEquals("[1, 2, 3]", IteratorKit.toString(list.iterator(), Objects::toString));
        Assertions.assertEquals("{1:2:3}", IteratorKit.toString(list.iterator(), Objects::toString, ":", "{", "}"));
    }

    @Test
    public void testForEach() {
        final List<Integer> list = new ArrayList<>();
        IteratorKit.forEach(Arrays.asList(1, 2, 3, 4).iterator(), list::add);
        Assertions.assertEquals(Arrays.asList(1, 2, 3, 4), list);
    }

    @Test
    public void getFirstNonNullTest() {
        final List<String> strings = ListKit.of(null, null, "123", "456", null);
        Assertions.assertEquals("123", CollKit.getFirstNoneNull(strings));
    }

    @Test
    public void fieldValueMapTest() {
        final List<Car> carList = ListKit.of(
                new Car("123", "大众"),
                new Car("345", "奔驰"),
                new Car("567", "路虎"));
        final Map<String, Car> carNameMap = IteratorKit.fieldValueMap(carList.iterator(), "carNumber");

        Assertions.assertEquals("大众", carNameMap.get("123").getCarName());
        Assertions.assertEquals("奔驰", carNameMap.get("345").getCarName());
        Assertions.assertEquals("路虎", carNameMap.get("567").getCarName());
    }

    @Test
    public void joinTest() {
        final List<String> list = ListKit.of("1", "2", "3", "4");
        final String join = IteratorKit.join(list.iterator(), ":");
        Assertions.assertEquals("1:2:3:4", join);

        final List<Integer> list1 = ListKit.of(1, 2, 3, 4);
        final String join1 = IteratorKit.join(list1.iterator(), ":");
        Assertions.assertEquals("1:2:3:4", join1);

        // 包装每个节点
        final List<String> list2 = ListKit.of("1", "2", "3", "4");
        final String join2 = IteratorKit.join(list2.iterator(), ":", "\"", "\"");
        Assertions.assertEquals("\"1\":\"2\":\"3\":\"4\"", join2);
    }

    @Test
    public void joinWithFuncTest() {
        final List<String> list = ListKit.of("1", "2", "3", "4");
        final String join = IteratorKit.join(list.iterator(), ":", String::valueOf);
        Assertions.assertEquals("1:2:3:4", join);
    }

    @Test
    public void joinWithNullTest() {
        final List<String> list = ListKit.of("1", null, "3", "4");
        final String join = IteratorKit.join(list.iterator(), ":", String::valueOf);
        Assertions.assertEquals("1:null:3:4", join);
    }

    @Test
    public void testToMap2() {
        final Map<String, Car> expectedMap = new HashMap<>();

        final Car bmw = new Car("123", "bmw");
        expectedMap.put("123", bmw);

        final Car benz = new Car("456", "benz");
        expectedMap.put("456", benz);

        final Map<String, Car> testMap = IteratorKit.toMap(Arrays.asList(bmw, benz), Car::getCarNumber);
        Assertions.assertEquals(expectedMap, testMap);
    }

    @Test
    public void getElementTypeTest() {
        final List<Integer> integers = Arrays.asList(null, 1);
        final Class<?> elementType = IteratorKit.getElementType(integers);
        Assertions.assertEquals(Integer.class, elementType);
    }

    @Test
    public void removeTest() {
        final List<String> obj2 = ListKit.of("3");
        final List<String> obj = ListKit.of("1", "3");

        IteratorKit.remove(obj.iterator(), (e) -> !obj2.contains(e));

        Assertions.assertEquals(1, obj.size());
        Assertions.assertEquals("3", obj.get(0));
    }

    @Test
    public void filteredTest() {
        final List<String> obj2 = ListKit.of("3");
        final List<String> obj = ListKit.of("1", "3");

        final FilterIterator<String> filtered = IteratorKit.filtered(obj.iterator(), obj2::contains);

        Assertions.assertEquals("3", filtered.next());
        Assertions.assertFalse(filtered.hasNext());
    }

    @Test
    public void filterToListTest() {
        final List<String> obj2 = ListKit.of("3");
        final List<String> obj = ListKit.of("1", "3");

        final List<String> filtered = IteratorKit.filterToList(obj.iterator(), obj2::contains);

        Assertions.assertEquals(1, filtered.size());
        Assertions.assertEquals("3", filtered.get(0));
    }

    @Test
    public void getTest() {
        final HashSet<String> set = SetKit.ofLinked("A", "B", "C", "D");
        final String str = IteratorKit.get(set.iterator(), 2);
        Assertions.assertEquals("C", str);
    }

    @Test
    public void testIsSubTrue() {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(IteratorKit.isSub(list, list), "Expected a list to be a subset of itself");

        final List<Integer> subList = Arrays.asList(1, 2, 3);
        assertTrue(IteratorKit.isSub(subList, list), "Expected subList to be a subset of list");

        assertFalse(IteratorKit.isSub(list, subList), "Expected subList to not be a subset of list due to extra elements");
    }

    @Test
    public void testIsSubFalseDueToMissingElements() {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        final List<Integer> subList = Arrays.asList(1, 2, 6);
        assertFalse(IteratorKit.isSub(subList, list), "Expected subList to not be a subset of list due to missing elements");
    }

    @Test
    public void testIsSubWithEmptySubList() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        final List<Integer> subList = Collections.emptyList();
        assertTrue(IteratorKit.isSub(subList, list), "Expected an empty subList to be considered a subset of list");

        assertFalse(IteratorKit.isSub(list, subList), "Expected subList to not be a subset of an empty list");
    }

    @Test
    public void testIsSubWithNullIterable() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        assertFalse(IteratorKit.isSub(null, list), "Expected null to not be considered a subset of list");
        assertFalse(IteratorKit.isSub(list, null), "Expected list to not be considered a subset of null");
        assertTrue(IteratorKit.isSub(null, null), "Expected null to not be considered a subset of null");
    }

    @RequiredArgsConstructor
    private static class Bean {
        private final Integer id;
        private final String name;
    }

    @Data
    @AllArgsConstructor
    public static class Car {
        private String carNumber;
        private String carName;
    }

}
