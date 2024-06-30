package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

import java.math.BigDecimal;
import java.util.*;

/**
 * {@link ArrayKit} 数组工具单元测试
 */
public class ArrayKitTest {

    @Test
    public void isEmptyTest() {
        final int[] a = {};
        Assertions.assertTrue(ArrayKit.isEmpty(a));
        Assertions.assertTrue(ArrayKit.isEmpty((Object) a));
        final int[] b = null;
        Assertions.assertTrue(ArrayKit.isEmpty(b));
        final Object c = null;
        Assertions.assertTrue(ArrayKit.isEmpty(c));

        Object d = new Object[]{"1", "2", 3, 4D};
        boolean isEmpty = ArrayKit.isEmpty(d);
        Assertions.assertFalse(isEmpty);
        d = new Object[0];
        isEmpty = ArrayKit.isEmpty(d);
        Assertions.assertTrue(isEmpty);
        d = null;
        isEmpty = ArrayKit.isEmpty(d);
        Assertions.assertTrue(isEmpty);

        // Object数组
        final Object[] e = new Object[]{"1", "2", 3, 4D};
        final boolean empty = ArrayKit.isEmpty(e);
        Assertions.assertFalse(empty);

        // 当这个对象并非数组对象且非`null`时，返回`false`，即当用户传入非数组对象，理解为单个元素的数组。
        final Object nonArrayObj = "a";
        Assertions.assertFalse(ArrayKit.isEmpty(nonArrayObj));
    }

    @Test
    public void isNotEmptyTest() {
        final int[] a = {1, 2};
        Assertions.assertTrue(ArrayKit.isNotEmpty(a));

        final String[] b = {"a", "b", "c"};
        Assertions.assertTrue(ArrayKit.isNotEmpty(b));

        final Object c = new Object[]{"1", "2", 3, 4D};
        Assertions.assertTrue(ArrayKit.isNotEmpty(c));
    }

    @Test
    public void newArrayTest() {
        final String[] newArray = ArrayKit.newArray(String.class, 3);
        Assertions.assertEquals(3, newArray.length);

        final Object[] newArray2 = ArrayKit.newArray(3);
        Assertions.assertEquals(3, newArray2.length);
    }

    @Test
    public void cloneTest() {
        final Integer[] b = {1, 2, 3};
        final Integer[] cloneB = ArrayKit.clone(b);
        Assertions.assertArrayEquals(b, cloneB);

        final int[] a = {1, 2, 3};
        final int[] clone = ArrayKit.clone(a);
        Assertions.assertArrayEquals(a, clone);

        final int[] clone1 = a.clone();
        Assertions.assertArrayEquals(a, clone1);
    }

    @Test
    public void filterEditTest() {
        final Integer[] a = {1, 2, 3, 4, 5, 6};
        final Integer[] filter = ArrayKit.edit(a, t -> (t % 2 == 0) ? t : null);
        Assertions.assertArrayEquals(filter, new Integer[]{2, 4, 6});
    }

    @Test
    public void filterTestForFilter() {
        final Integer[] a = {1, 2, 3, 4, 5, 6};
        final Integer[] filter = ArrayKit.filter(a, t -> t % 2 == 0);
        Assertions.assertArrayEquals(filter, new Integer[]{2, 4, 6});
    }

    @Test
    public void editTest() {
        final Integer[] a = {1, 2, 3, 4, 5, 6};
        final Integer[] filter = ArrayKit.edit(a, t -> (t % 2 == 0) ? t * 10 : t);
        Assertions.assertArrayEquals(filter, new Integer[]{1, 20, 3, 40, 5, 60});
    }

    @Test
    public void indexOfTest() {
        final Integer[] a = {1, 2, 3, 4, 5, 6};
        final int index = ArrayKit.indexOf(a, 3);
        Assertions.assertEquals(2, index);

        final long[] b = {1, 2, 3, 4, 5, 6};
        final int index2 = ArrayKit.indexOf(b, 3);
        Assertions.assertEquals(2, index2);
    }

    @Test
    public void lastIndexOfTest() {
        final Integer[] a = {1, 2, 3, 4, 3, 6};
        final int index = ArrayKit.lastIndexOf(a, 3);
        Assertions.assertEquals(4, index);

        final long[] b = {1, 2, 3, 4, 3, 6};
        final int index2 = ArrayKit.lastIndexOf(b, 3);
        Assertions.assertEquals(4, index2);
    }

    @Test
    public void containsTest() {
        final Integer[] a = {1, 2, 3, 4, 3, 6};
        final boolean contains = ArrayKit.contains(a, 3);
        Assertions.assertTrue(contains);

        final long[] b = {1, 2, 3, 4, 3, 6};
        final boolean contains2 = ArrayKit.contains(b, 3);
        Assertions.assertTrue(contains2);
    }

    @Test
    public void containsAnyTest() {
        final Integer[] a = {1, 2, 3, 4, 3, 6};
        boolean contains = ArrayKit.containsAny(a, 4, 10, 40);
        Assertions.assertTrue(contains);

        contains = ArrayKit.containsAny(a, 10, 40);
        Assertions.assertFalse(contains);
    }

    @Test
    public void containsAllTest() {
        final Integer[] a = {1, 2, 3, 4, 3, 6};
        boolean contains = ArrayKit.containsAll(a, 4, 2, 6);
        // 提供的可变参数中元素顺序不需要一致
        Assertions.assertTrue(contains);

        contains = ArrayKit.containsAll(a, 1, 2, 3, 5);
        Assertions.assertFalse(contains);
    }

    @Test
    void containsIgnoreCaseTest() {
        final String[] keys = {"a", "B", "c"};
        final boolean b = ArrayKit.containsIgnoreCase(keys, "b");
        Assertions.assertTrue(b);
    }

    @Test
    public void testContainsIgnoreCaseWithEmptyArray() {
        final CharSequence[] array = new CharSequence[0];
        final CharSequence value = "test";
        final boolean result = ArrayKit.containsIgnoreCase(array, value);
        Assertions.assertFalse(result, "Expected the result to be false for an empty array.");
    }

    @Test
    public void testContainsIgnoreCaseWithNullValue() {
        final CharSequence[] array = {"Hello", "World"};
        final CharSequence value = null;
        final boolean result = ArrayKit.containsIgnoreCase(array, value);
        Assertions.assertFalse(result, "Expected the result to be false when the value is null.");
    }

    @Test
    public void testContainsIgnoreCaseWithExistingValue() {
        final CharSequence[] array = {"Hello", "World"};
        final CharSequence value = "world";
        final boolean result = ArrayKit.containsIgnoreCase(array, value);
        Assertions.assertTrue(result, "Expected the result to be true when the value exists in the array.");
    }

    @Test
    public void testContainsIgnoreCaseWithNonExistingValue() {
        final CharSequence[] array = {"Hello", "World"};
        final CharSequence value = "Java";
        final boolean result = ArrayKit.containsIgnoreCase(array, value);
        Assertions.assertFalse(result, "Expected the result to be false when the value does not exist in the array.");
    }

    @Test
    public void testContainsIgnoreCaseWithCaseSensitiveValue() {
        final CharSequence[] array = {"Hello", "World"};
        final CharSequence value = "HELLO";
        final boolean result = ArrayKit.containsIgnoreCase(array, value);
        Assertions.assertTrue(result, "Expected the result to be true when the value exists in the array with different case sensitivity.");
    }

    @Test
    public void zipTest() {
        final String[] keys = {"a", "b", "c"};
        final Integer[] values = {1, 2, 3};
        final Map<String, Integer> map = ArrayKit.zip(keys, values, true);
        Assertions.assertEquals(Objects.requireNonNull(map).toString(), "{a=1, b=2, c=3}");
    }

    @Test
    public void mapToArrayTest() {
        final String[] keys = {"a", "b", "c"};
        final Integer[] integers = ArrayKit.mapToArray(keys, String::length, Integer[]::new);
        Assertions.assertArrayEquals(integers, new Integer[]{1, 1, 1});
    }

    @Test
    public void castTest() {
        final Object[] values = {"1", "2", "3"};
        final String[] cast = (String[]) ArrayKit.cast(String.class, values);
        Assertions.assertEquals(values[0], cast[0]);
        Assertions.assertEquals(values[1], cast[1]);
        Assertions.assertEquals(values[2], cast[2]);
    }

    @Test
    public void maxTest() {
        final int max = ArrayKit.max(1, 2, 13, 4, 5);
        Assertions.assertEquals(13, max);

        final long maxLong = ArrayKit.max(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(13, maxLong);

        final double maxDouble = ArrayKit.max(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(13.0, maxDouble, 0);

        final BigDecimal one = new BigDecimal("1.00");
        final BigDecimal two = new BigDecimal("2.0");
        final BigDecimal three = new BigDecimal("3");
        final BigDecimal[] bigDecimals = {two, one, three};

        final BigDecimal minAccuracy = ArrayKit.min(bigDecimals, Comparator.comparingInt(BigDecimal::scale));
        Assertions.assertEquals(minAccuracy, three);

        final BigDecimal maxAccuracy = ArrayKit.max(bigDecimals, Comparator.comparingInt(BigDecimal::scale));
        Assertions.assertEquals(maxAccuracy, one);
    }

    @Test
    public void minTest() {
        final int min = ArrayKit.min(1, 2, 13, 4, 5);
        Assertions.assertEquals(1, min);

        final long minLong = ArrayKit.min(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(1, minLong);

        final double minDouble = ArrayKit.min(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(1.0, minDouble, 0);
    }

    @Test
    public void appendTest() {
        final String[] a = {"1", "2", "3", "4"};
        final String[] b = {"a", "b", "c"};

        final String[] result = ArrayKit.append(a, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);
    }

    @Test
    public void appendTest2() {
        final String[] a = {"1", "2", "3", "4"};

        final String[] result = ArrayKit.append(a, "a", "b", "c");
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);
    }

    @Test
    public void insertTest() {
        final String[] a = {"1", "2", "3", "4"};
        final String[] b = {"a", "b", "c"};

        // 在-1的位置插入，相当于在3的位置插入
        String[] result = ArrayKit.insert(a, -1, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "a", "b", "c", "4"}, result);

        // 在第0个位置插入，即在数组前追加
        result = ArrayKit.insert(a, 0, b);
        Assertions.assertArrayEquals(new String[]{"a", "b", "c", "1", "2", "3", "4"}, result);

        // 在第2个位置插入，即"3"之前
        result = ArrayKit.insert(a, 2, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "a", "b", "c", "3", "4"}, result);

        // 在第4个位置插入，即"4"之后，相当于追加
        result = ArrayKit.insert(a, 4, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

        // 在第5个位置插入，由于数组长度为4，因此补充null
        result = ArrayKit.insert(a, 5, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", null, "a", "b", "c"}, result);
    }

    @Test
    public void joinTest() {
        final String[] array = {"aa", "bb", "cc", "dd"};
        final String join = ArrayKit.join(array, ",", "[", "]");
        Assertions.assertEquals("[aa],[bb],[cc],[dd]", join);

        final Object array2 = new String[]{"aa", "bb", "cc", "dd"};
        final String join2 = ArrayKit.join(array2, ",");
        Assertions.assertEquals("aa,bb,cc,dd", join2);
    }

    @Test
    public void testJoinWithNullElement() {
        final String[] array = {"Java", null, "Python"};
        final String result = ArrayKit.join(array, ", ", value -> value == null ? "null" : value);
        Assertions.assertEquals("Java, null, Python", result);
    }

    @Test
    public void testJoinWithEmptyArray() {
        final String[] array = {};
        final String result = ArrayKit.join(array, ", ", String::toUpperCase);
        Assertions.assertEquals("", result);
    }

    @Test
    public void testJoinWithoutEditor() {
        final Integer[] array = {1, 2, 3};
        final String result = ArrayKit.join(array, ", ");
        Assertions.assertEquals("1, 2, 3", result);
    }

    @Test
    public void testJoinWithEditor() {
        final String[] array = {"java", "scala", "kotlin"};
        final String result = ArrayKit.join(array, " -> ", String::toUpperCase);
        Assertions.assertEquals("JAVA -> SCALA -> KOTLIN", result);
    }

    @Test
    public void testJoinWithNullConjunction() {
        final String[] array = {"one", "two", "three"};
        final String result = ArrayKit.join(array, null, value -> value + "!");
        Assertions.assertEquals("one!two!three!", result);
    }

    @Test
    public void getArrayTypeTest() {
        Class<?> arrayType = ArrayKit.getArrayType(int.class);
        Assertions.assertSame(int[].class, arrayType);

        arrayType = ArrayKit.getArrayType(String.class);
        Assertions.assertSame(String[].class, arrayType);
    }

    @Test
    public void distinctTest() {
        final String[] array = {"aa", "bb", "cc", "dd", "bb", "dd"};
        final String[] distinct = ArrayKit.distinct(array);
        Assertions.assertArrayEquals(new String[]{"aa", "bb", "cc", "dd"}, distinct);
    }

    @Test
    public void distinctByFunctionTest() {
        final String[] array = {"aa", "Aa", "BB", "bb"};

        // 覆盖模式下，保留最后加入的两个元素
        String[] distinct = ArrayKit.distinct(array, String::toLowerCase, true);
        Assertions.assertArrayEquals(new String[]{"Aa", "bb"}, distinct);

        // 忽略模式下，保留最早加入的两个元素
        distinct = ArrayKit.distinct(array, String::toLowerCase, false);
        Assertions.assertArrayEquals(new String[]{"aa", "BB"}, distinct);
    }

    @Test
    public void toStingTest() {
        final int[] a = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(a));
        final long[] b = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(b));
        final short[] c = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(c));
        final double[] d = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayKit.toString(d));
        final byte[] e = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(e));
        final boolean[] f = {true, false, true, true, true};
        Assertions.assertEquals("[true, false, true, true, true]", ArrayKit.toString(f));
        final float[] g = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayKit.toString(g));
        final char[] h = {'a', 'b', '你', '好', '1'};
        Assertions.assertEquals("[a, b, 你, 好, 1]", ArrayKit.toString(h));

        final String[] array = {"aa", "bb", "cc", "dd", "bb", "dd"};
        Assertions.assertEquals("[aa, bb, cc, dd, bb, dd]", ArrayKit.toString(array));
    }

    @Test
    public void toArrayTest() {
        final List<String> list = ListKit.of("A", "B", "C", "D");
        final String[] array = ArrayKit.ofArray(list, String.class);
        Assertions.assertEquals("A", array[0]);
        Assertions.assertEquals("B", array[1]);
        Assertions.assertEquals("C", array[2]);
        Assertions.assertEquals("D", array[3]);
    }

    @Test
    public void addAllTest() {
        final int[] ints = ArrayKit.addAll(new int[]{1, 2, 3}, new int[]{4, 5, 6});
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, ints);
    }

    @Test
    public void isAllNotNullTest() {
        final String[] allNotNull = {"aa", "bb", "cc", "dd", "bb", "dd"};
        Assertions.assertTrue(ArrayKit.isAllNotNull(allNotNull));
        final String[] hasNull = {"aa", "bb", "cc", null, "bb", "dd"};
        Assertions.assertFalse(ArrayKit.isAllNotNull(hasNull));
    }

    @Test
    void firstNonNullTest() {
        final String[] a = {null, null, "cc", null, "bb", "dd"};
        final String s = ArrayKit.firstNonNull(a);
        Assertions.assertEquals("cc", s);
    }

    @Test
    public void indexOfSubTest() {
        final Integer[] a = {0x12, 0x34, 0x56, 0x78, 0x9A};
        final Integer[] b = {0x56, 0x78};
        final Integer[] c = {0x12, 0x56};
        final Integer[] d = {0x78, 0x9A};
        final Integer[] e = {0x78, 0x9A, 0x10};

        int i = ArrayKit.indexOfSub(a, b);
        Assertions.assertEquals(2, i);

        i = ArrayKit.indexOfSub(a, c);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.indexOfSub(a, d);
        Assertions.assertEquals(3, i);

        i = ArrayKit.indexOfSub(a, e);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.indexOfSub(a, null);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.indexOfSub(null, null);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.indexOfSub(null, b);
        Assertions.assertEquals(-1, i);
    }

    @Test
    public void indexOfSubTest2() {
        final Integer[] a = {0x12, 0x56, 0x34, 0x56, 0x78, 0x9A};
        final Integer[] b = {0x56, 0x78};
        final int i = ArrayKit.indexOfSub(a, b);
        Assertions.assertEquals(3, i);
    }

    @Test
    public void lastIndexOfSubTest() {
        final Integer[] a = {0x12, 0x34, 0x56, 0x78, 0x9A};
        final Integer[] b = {0x56, 0x78};
        final Integer[] c = {0x12, 0x56};
        final Integer[] d = {0x78, 0x9A};
        final Integer[] e = {0x78, 0x9A, 0x10};

        int i = ArrayKit.lastIndexOfSub(a, b);
        Assertions.assertEquals(2, i);

        i = ArrayKit.lastIndexOfSub(a, c);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.lastIndexOfSub(a, d);
        Assertions.assertEquals(3, i);

        i = ArrayKit.lastIndexOfSub(a, e);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.lastIndexOfSub(a, null);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.lastIndexOfSub(null, null);
        Assertions.assertEquals(-1, i);

        i = ArrayKit.lastIndexOfSub(null, b);
        Assertions.assertEquals(-1, i);
    }

    @Test
    public void lastIndexOfSubTest2() {
        final Integer[] a = {0x12, 0x56, 0x78, 0x56, 0x21, 0x9A};
        final Integer[] b = {0x56, 0x78};
        final int i = ArrayKit.indexOfSub(a, b);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void reverseTest() {
        final int[] a = {1, 2, 3, 4};
        final int[] reverse = ArrayKit.reverse(a);
        Assertions.assertArrayEquals(new int[]{4, 3, 2, 1}, reverse);
    }

    @Test
    public void reverseTest2s() {
        final Object[] a = {"1", '2', "3", 4};
        final Object[] reverse = ArrayKit.reverse(a);
        Assertions.assertArrayEquals(new Object[]{4, "3", '2', "1"}, reverse);
    }

    @Test
    public void removeEmptyTest() {
        final String[] a = {"a", "b", "", null, " ", "c"};
        final String[] resultA = {"a", "b", " ", "c"};
        Assertions.assertArrayEquals(ArrayKit.removeEmpty(a), resultA);
    }

    @Test
    public void removeBlankTest() {
        final String[] a = {"a", "b", "", null, " ", "c"};
        final String[] resultA = {"a", "b", "c"};
        Assertions.assertArrayEquals(ArrayKit.removeBlank(a), resultA);
    }

    @Test
    public void nullToEmptyTest() {
        final String[] a = {"a", "b", "", null, " ", "c"};
        final String[] resultA = {"a", "b", "", "", " ", "c"};
        Assertions.assertArrayEquals(ArrayKit.nullToEmpty(a), resultA);
    }

    @Test
    public void wrapTest() {
        final Object a = new int[]{1, 2, 3, 4};
        final Object[] wrapA = ArrayKit.wrap(a);
        for (final Object o : wrapA) {
            Assertions.assertSame(Integer.class, o);
        }
    }

    @Test
    public void wrapIntTest() {
        final int[] a = new int[]{1, 2, 3, 4};
        final Integer[] wrapA = ArrayKit.wrap(a);
        for (final Integer o : wrapA) {
            Assertions.assertSame(Integer.class, o);
        }
    }

    @Test
    public void unWrapIntTest() {
        final Integer[] a = new Integer[]{1, 2, 3, 4};
        final int[] wrapA = ArrayKit.unWrap(a);
        final Class<?> componentType = wrapA.getClass().getComponentType();
        Assertions.assertEquals(int.class, componentType);
    }

    @Test
    public void splitTest() {
        final byte[] array = new byte[1024];
        final byte[][] arrayAfterSplit = ArrayKit.split(array, 500);
        Assertions.assertEquals(3, arrayAfterSplit.length);
        Assertions.assertEquals(24, arrayAfterSplit[2].length);

        final byte[] arr = {1, 2, 3, 4, 5, 6, 7};
        Assertions.assertArrayEquals(new byte[][]{{1, 2, 3, 4, 5, 6, 7}}, ArrayKit.split(arr, 8));
        Assertions.assertArrayEquals(new byte[][]{{1, 2, 3, 4, 5, 6, 7}}, ArrayKit.split(arr, 7));
        Assertions.assertArrayEquals(new byte[][]{{1, 2, 3, 4}, {5, 6, 7}}, ArrayKit.split(arr, 4));
        Assertions.assertArrayEquals(new byte[][]{{1, 2, 3}, {4, 5, 6}, {7}}, ArrayKit.split(arr, 3));
        Assertions.assertArrayEquals(new byte[][]{{1, 2}, {3, 4}, {5, 6}, {7}}, ArrayKit.split(arr, 2));
        Assertions.assertArrayEquals(new byte[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}}, ArrayKit.split(arr, 1));
    }

    @Test
    public void getTest() {
        final String[] a = {"a", "b", "c"};
        final Object o = ArrayKit.get(a, -1);
        Assertions.assertEquals("c", o);
    }

    @Test
    public void getByPredicateTest() {
        final String[] a = {"a", "b", "c"};
        final Object o = ArrayKit.get(a, "b"::equals);
        Assertions.assertEquals("b", o);
    }

    @Test
    public void replaceTest() {
        final String[] a = {"1", "2", "3", "4"};
        final String[] b = {"a", "b", "c"};

        // 在小于0的位置，-1的位置插入，返回b+a，新数组
        String[] result = ArrayKit.replace(a, -1, b);
        Assertions.assertArrayEquals(new String[]{"a", "b", "c", "1", "2", "3", "4"}, result);

        // 在第0个位置开始替换，返回a
        result = ArrayKit.replace(ArrayKit.clone(a), 0, b);
        Assertions.assertArrayEquals(new String[]{"a", "b", "c", "4"}, result);

        // 在第1个位置替换，即"2"开始
        result = ArrayKit.replace(ArrayKit.clone(a), 1, b);
        Assertions.assertArrayEquals(new String[]{"1", "a", "b", "c"}, result);

        // 在第2个位置插入，即"3"之后
        result = ArrayKit.replace(ArrayKit.clone(a), 2, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "a", "b", "c"}, result);

        // 在第3个位置插入，即"4"之后
        result = ArrayKit.replace(ArrayKit.clone(a), 3, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "a", "b", "c"}, result);

        // 在第4个位置插入，数组长度为4，在索引4出替换即两个数组相加
        result = ArrayKit.replace(ArrayKit.clone(a), 4, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

        // 在大于3个位置插入，数组长度为4，即两个数组相加
        result = ArrayKit.replace(ArrayKit.clone(a), 5, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);

        final String[] e = null;
        final String[] f = {"a", "b", "c"};

        // e为null 返回 f
        result = ArrayKit.replace(e, -1, f);
        Assertions.assertArrayEquals(f, result);

        final String[] g = {"a", "b", "c"};
        final String[] h = null;

        // h为null 返回 g
        result = ArrayKit.replace(g, 0, h);
        Assertions.assertArrayEquals(g, result);
    }

    @Test
    public void replaceTest2() {
        int[] a = new int[0];
        a = ArrayKit.replace(a, 0, new int[]{1});
        Assertions.assertEquals(1, a.length);
    }

    @Test
    public void setOrAppendTest() {
        final String[] arr = new String[0];
        final String[] newArr = ArrayKit.setOrAppend(arr, 0, "Good");// ClassCastException
        Assertions.assertArrayEquals(new String[]{"Good"}, newArr);

        // 非空数组替换第一个元素
        int[] arr2 = new int[]{1};
        int[] o = ArrayKit.setOrAppend(arr2, 0, 2);
        Assertions.assertArrayEquals(new int[]{2}, o);

        // 空数组追加
        arr2 = new int[0];
        o = ArrayKit.setOrAppend(arr2, 0, 2);
        Assertions.assertArrayEquals(new int[]{2}, o);
    }

    @Test
    void setOrPaddingTest() {
        final String[] arr = new String[0];
        final String[] newArr = ArrayKit.setOrPadding(arr, 2, "Good");
        Assertions.assertArrayEquals(new String[]{null, null, "Good"}, newArr);
    }

    @Test
    void setOrPaddingTest2() {
        final String[] arr = new String[0];
        final String[] newArr = ArrayKit.setOrPadding(arr, 2, "Good");
        Assertions.assertArrayEquals(new String[]{null, null, "Good"}, newArr);
    }

    @Test
    void setOrPaddingTest3() {
        final String[] arr = new String[0];
        final String[] newArr = ArrayKit.setOrPadding(arr, 2, "Good", "pad");
        Assertions.assertArrayEquals(new String[]{"pad", "pad", "Good"}, newArr);
    }

    @Test
    public void getAnyTest() {
        final String[] a = {"a", "b", "c", "d", "e"};
        final Object o = ArrayKit.getAny(a, 3, 4);
        final String[] resultO = (String[]) o;
        final String[] c = {"d", "e"};
        Assertions.assertTrue(ArrayKit.containsAll(c, resultO[0], resultO[1]));
    }

    @Test
    void hasNullTest() {
        final String[] a = {"e", null};
        Assertions.assertTrue(ArrayKit.hasNull(a));
    }

    @Test
    public void hasNonNullTest() {
        String[] a = {null, "e"};
        Assertions.assertTrue(ArrayKit.hasNonNull(a));

        a = new String[]{null, null};
        Assertions.assertFalse(ArrayKit.hasNonNull(a));

        a = new String[]{"", null};
        Assertions.assertTrue(ArrayKit.hasNonNull(a));

        a = new String[]{null};
        Assertions.assertFalse(ArrayKit.hasNonNull(a));

        a = new String[]{};
        Assertions.assertFalse(ArrayKit.hasNonNull(a));

        a = null;
        Assertions.assertFalse(ArrayKit.hasNonNull(a));
    }

    @Test
    public void isAllNullTest() {
        String[] a = {null, "e"};
        Assertions.assertFalse(ArrayKit.isAllNull(a));

        a = new String[]{null, null};
        Assertions.assertTrue(ArrayKit.isAllNull(a));

        a = new String[]{"", null};
        Assertions.assertFalse(ArrayKit.isAllNull(a));

        a = new String[]{null};
        Assertions.assertTrue(ArrayKit.isAllNull(a));

        a = new String[]{};
        Assertions.assertTrue(ArrayKit.isAllNull(a));

        a = null;
        Assertions.assertTrue(ArrayKit.isAllNull(a));
    }

    @Test
    public void insertPrimitiveTest() {
        final boolean[] booleans = new boolean[10];
        final byte[] bytes = new byte[10];
        final char[] chars = new char[10];
        final short[] shorts = new short[10];
        final int[] ints = new int[10];
        final long[] longs = new long[10];
        final float[] floats = new float[10];
        final double[] doubles = new double[10];

        final boolean[] insert1 = ArrayKit.insert(booleans, 0, 0, 1, 2);
        Assertions.assertNotNull(insert1);
        final byte[] insert2 = ArrayKit.insert(bytes, 0, 1, 2, 3);
        Assertions.assertNotNull(insert2);
        final char[] insert3 = ArrayKit.insert(chars, 0, 1, 2, 3);
        Assertions.assertNotNull(insert3);
        final short[] insert4 = ArrayKit.insert(shorts, 0, 1, 2, 3);
        Assertions.assertNotNull(insert4);
        final int[] insert5 = ArrayKit.insert(ints, 0, 1, 2, 3);
        Assertions.assertNotNull(insert5);
        final long[] insert6 = ArrayKit.insert(longs, 0, 1, 2, 3);
        Assertions.assertNotNull(insert6);
        final float[] insert7 = ArrayKit.insert(floats, 0, 1, 2, 3);
        Assertions.assertNotNull(insert7);
        final double[] insert8 = ArrayKit.insert(doubles, 0, 1, 2, 3);
        Assertions.assertNotNull(insert8);
    }

    @Test
    public void subTest() {
        final int[] arr = {1, 2, 3, 4, 5};
        final int[] empty = new int[0];
        Assertions.assertArrayEquals(empty, ArrayKit.sub(arr, 2, 2));
        Assertions.assertArrayEquals(empty, ArrayKit.sub(arr, 5, 5));
        Assertions.assertArrayEquals(empty, ArrayKit.sub(arr, 5, 7));
        Assertions.assertArrayEquals(arr, ArrayKit.sub(arr, 0, 5));
        Assertions.assertArrayEquals(arr, ArrayKit.sub(arr, 5, 0));
        Assertions.assertArrayEquals(arr, ArrayKit.sub(arr, 0, 7));
        Assertions.assertArrayEquals(new int[]{1}, ArrayKit.sub(arr, 0, 1));
        Assertions.assertArrayEquals(new int[]{5}, ArrayKit.sub(arr, 4, 5));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, 1, 4));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, 4, 1));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, 1, -1));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, -1, 1));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, -1, 1));
        Assertions.assertArrayEquals(new int[]{2, 3, 4}, ArrayKit.sub(arr, -4, -1));
    }

    @Test
    public void isSortedTest() {
        final Integer[] a = {1, 1, 2, 2, 2, 3, 3};
        Assertions.assertTrue(ArrayKit.isSorted(a));
        Assertions.assertTrue(ArrayKit.isSorted(a, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(a, null));

        final Integer[] b = {1, 1, 1, 1, 1, 1};
        Assertions.assertTrue(ArrayKit.isSorted(b));
        Assertions.assertTrue(ArrayKit.isSorted(b, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(a, null));

        final Integer[] c = {3, 3, 2, 2, 2, 1, 1};
        Assertions.assertTrue(ArrayKit.isSorted(c));
        Assertions.assertTrue(ArrayKit.isSorted(c, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(a, null));

        Assertions.assertFalse(ArrayKit.isSorted(null));
        Assertions.assertFalse(ArrayKit.isSorted(null, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(null, null));

        final Integer[] d = {};
        Assertions.assertFalse(ArrayKit.isSorted(d));
        Assertions.assertFalse(ArrayKit.isSorted(d, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(d, null));

        final Integer[] e = {1};
        Assertions.assertTrue(ArrayKit.isSorted(e));
        Assertions.assertTrue(ArrayKit.isSorted(e, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(e, null));

        final Integer[] f = {1, 2};
        Assertions.assertTrue(ArrayKit.isSorted(f));
        Assertions.assertTrue(ArrayKit.isSorted(f, Integer::compareTo));
        Assertions.assertFalse(ArrayKit.isSorted(f, null));
    }

    @Test
    public void hasSameElementTest() {
        final Integer[] a = {1, 1};
        Assertions.assertTrue(ArrayKit.hasSameElement(a));

        final String[] b = {"a", "b", "c"};
        Assertions.assertFalse(ArrayKit.hasSameElement(b));

        final Object[] c = new Object[]{"1", "2", 2, 4D};
        Assertions.assertFalse(ArrayKit.hasSameElement(c));

        final Object[] d = new Object[]{"1", "2", "2", 4D};
        Assertions.assertTrue(ArrayKit.hasSameElement(d));

        final Object[] e = new Object[]{"1", 2, 2, 4D};
        Assertions.assertTrue(ArrayKit.hasSameElement(e));

    }

    @Test
    public void startWithTest() {
        boolean b = ArrayKit.startWith(new String[]{}, new String[]{});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith(new String[]{"1", "2", "3"}, new String[]{"1"});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith(new String[]{"1"}, new String[]{"1"});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith((String[]) null, null);
        Assertions.assertTrue(b);
    }

    @SuppressWarnings({"RedundantArrayCreation", "ConfusingArgumentToVarargsMethod"})
    @Test
    public void startWithTest2() {
        boolean b = ArrayKit.startWith(new int[]{}, new int[]{});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith(new int[]{1, 2, 3}, new int[]{1});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith(new int[]{1}, new int[]{1});
        Assertions.assertTrue(b);

        b = ArrayKit.startWith((int[]) null, null);
        Assertions.assertTrue(b);
    }

    @Test
    public void equalsTest() {
        final boolean b = ArrayKit.equals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        Assertions.assertTrue(b);
    }

    @Test
    public void copyOfRangeTest() {
        final String a = "aIDAT";
        final byte[] bytes1 = Arrays.copyOfRange(a.getBytes(Charset.UTF_8), 1, 1 + 4);

        Assertions.assertEquals(new String(bytes1),
                new String(a.getBytes(Charset.UTF_8), 1, 4));
    }

    @Test
    void copyTest() {
        final String[] dest = new String[3];
        ArrayKit.copy(new String[]{"a", "b"}, dest);
        Assertions.assertArrayEquals(new String[]{"a", "b", null}, dest);
    }

    @Test
    void copyTest2() {
        final String[] dest = new String[3];
        ArrayKit.copy(new String[]{"a", "b"}, dest, 1);
        Assertions.assertArrayEquals(new String[]{"a", null, null}, dest);
    }

    @Test
    public void regionMatchesTest() {
        final byte[] a = new byte[]{1, 2, 3, 4, 5};
        final byte[] b = new byte[]{2, 3, 4};

        Assertions.assertTrue(ArrayKit.regionMatches(a, 1, b, 0, 1));
        Assertions.assertTrue(ArrayKit.regionMatches(a, 1, b, 0, 2));
        Assertions.assertTrue(ArrayKit.regionMatches(a, 1, b, 0, 3));
        Assertions.assertTrue(ArrayKit.isSubEquals(a, 1, b));

        Assertions.assertFalse(ArrayKit.regionMatches(a, 2, b, 0, 2));
        Assertions.assertFalse(ArrayKit.regionMatches(a, 3, b, 0, 2));
    }

    @Test
    public void hasEmptyVarargsTest() {
        Assertions.assertFalse(ArrayKit.hasEmptyVarargs(1, 2, 3, 4, 5));
        Assertions.assertTrue(ArrayKit.hasEmptyVarargs("", " ", "	"));
        Assertions.assertTrue(ArrayKit.hasEmptyVarargs("", "apple", "pear"));
    }

    @Test
    void hasEmptyTest() {
        final String[] a = {"", "a"};
        Assertions.assertTrue(ArrayKit.hasEmpty(a));

        Object[] b = {"a", new ArrayList<>()};
        Assertions.assertTrue(ArrayKit.hasEmpty(b));

        b = new Object[]{"a", new HashMap<>()};
        Assertions.assertTrue(ArrayKit.hasEmpty(b));
    }

    @Test
    public void isAllEmptyTest() {
        Assertions.assertFalse(ArrayKit.isAllEmptyVarargs("apple", "pear", "", "orange"));
    }

    @Test
    void emptyCountTest() {
        final Object[] b = {"a", new ArrayList<>(), new HashMap<>(), new int[0]};
        final int emptyCount = ArrayKit.emptyCount(b);
        Assertions.assertEquals(3, emptyCount);
    }

    @Test
    void hasBlankTest() {
        final String[] a = {"  ", "aa"};
        Assertions.assertTrue(ArrayKit.hasBlank(a));
    }

    @Test
    void isAllBlankTest() {
        final String[] a = {"  ", " ", ""};
        Assertions.assertTrue(ArrayKit.isAllBlank(a));
    }
}
