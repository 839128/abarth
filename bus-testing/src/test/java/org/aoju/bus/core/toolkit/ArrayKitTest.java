package org.aoju.bus.core.toolkit;

import org.aoju.bus.core.lang.Editor;
import org.aoju.bus.core.lang.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * {@link ArrayKit} 数组工具单元测试
 */
public class ArrayKitTest {

    @Test
    public void isEmptyTest() {
        int[] a = {};
        Assertions.assertTrue(ArrayKit.isEmpty(a));
        Assertions.assertTrue(ArrayKit.isEmpty((Object) a));
        int[] b = null;
        Assertions.assertTrue(ArrayKit.isEmpty(b));
        Object c = null;
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
    }

    @Test
    public void isNotEmptyTest() {
        int[] a = {1, 2};
        Assertions.assertTrue(ArrayKit.isNotEmpty(a));
    }

    @Test
    public void newArrayTest() {
        String[] newArray = ArrayKit.newArray(String.class, 3);
        Assertions.assertEquals(3, newArray.length);
    }

    @Test
    public void cloneTest() {
        Integer[] b = {1, 2, 3};
        Integer[] cloneB = ArrayKit.clone(b);
        Assertions.assertArrayEquals(b, cloneB);

        int[] a = {1, 2, 3};
        int[] clone = ArrayKit.clone(a);
        Assertions.assertArrayEquals(a, clone);
    }

    @Test
    public void filterTest() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Integer[] filter = ArrayKit.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assertions.assertArrayEquals(filter, new Integer[]{2, 4, 6});
    }

    @Test
    public void filterTestForFilter() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Integer[] filter = ArrayKit.filter(a, (Filter<Integer>) t -> t % 2 == 0);
        Assertions.assertArrayEquals(filter, new Integer[]{2, 4, 6});
    }

    @Test
    public void filterTestForEditor() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Integer[] filter = ArrayKit.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t * 10 : t);
        Assertions.assertArrayEquals(filter, new Integer[]{1, 20, 3, 40, 5, 60});
    }

    @Test
    public void indexOfTest() {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        int index = ArrayKit.indexOf(a, 3);
        Assertions.assertEquals(2, index);

        long[] b = {1, 2, 3, 4, 5, 6};
        int index2 = ArrayKit.indexOf(b, 3);
        Assertions.assertEquals(2, index2);
    }

    @Test
    public void lastIndexOfTest() {
        Integer[] a = {1, 2, 3, 4, 3, 6};
        int index = ArrayKit.lastIndexOf(a, 3);
        Assertions.assertEquals(4, index);

        long[] b = {1, 2, 3, 4, 3, 6};
        int index2 = ArrayKit.lastIndexOf(b, 3);
        Assertions.assertEquals(4, index2);
    }

    @Test
    public void containsTest() {
        Integer[] a = {1, 2, 3, 4, 3, 6};
        boolean contains = ArrayKit.contains(a, 3);
        Assertions.assertTrue(contains);

        long[] b = {1, 2, 3, 4, 3, 6};
        boolean contains2 = ArrayKit.contains(b, 3);
        Assertions.assertTrue(contains2);
    }

    @Test
    public void mapTest() {
        String[] keys = {"a", "b", "c"};
        Integer[] values = {1, 2, 3};
        Map<String, Integer> map = ArrayKit.zip(keys, values, true);
        Assertions.assertEquals(map.toString(), "{a=1, b=2, c=3}");
    }

    @Test
    public void castTest() {
        Object[] values = {"1", "2", "3"};
        String[] cast = (String[]) ArrayKit.cast(String.class, values);
        Assertions.assertEquals(values[0], cast[0]);
        Assertions.assertEquals(values[1], cast[1]);
        Assertions.assertEquals(values[2], cast[2]);
    }

    @Test
    public void rangeTest() {
        int[] range = ArrayKit.range(0, 10);
        Assertions.assertEquals(0, range[0]);
        Assertions.assertEquals(1, range[1]);
        Assertions.assertEquals(2, range[2]);
        Assertions.assertEquals(3, range[3]);
        Assertions.assertEquals(4, range[4]);
        Assertions.assertEquals(5, range[5]);
        Assertions.assertEquals(6, range[6]);
        Assertions.assertEquals(7, range[7]);
        Assertions.assertEquals(8, range[8]);
        Assertions.assertEquals(9, range[9]);
    }

    @Test
    public void maxTest() {
        int max = ArrayKit.max(1, 2, 13, 4, 5);
        Assertions.assertEquals(13, max);

        long maxLong = ArrayKit.max(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(13, maxLong);

        double maxDouble = ArrayKit.max(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(13.0, maxDouble, 2);
    }

    @Test
    public void minTest() {
        int min = ArrayKit.min(1, 2, 13, 4, 5);
        Assertions.assertEquals(1, min);

        long minLong = ArrayKit.min(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(1, minLong);

        double minDouble = ArrayKit.min(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(1.0, minDouble, 2);
    }

    @Test
    public void appendTest() {
        String[] a = {"1", "2", "3", "4"};
        String[] b = {"a", "b", "c"};

        String[] result = ArrayKit.append(a, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", "a", "b", "c"}, result);
    }

    @Test
    public void insertTest() {
        String[] a = {"1", "2", "3", "4"};
        String[] b = {"a", "b", "c"};

        // 在-1位置插入，相当于在3位置插入
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

        // 在第5个位置插入，由于数组长度为4，因此补null
        result = ArrayKit.insert(a, 5, b);
        Assertions.assertArrayEquals(new String[]{"1", "2", "3", "4", null, "a", "b", "c"}, result);
    }

    @Test
    public void joinTest() {
        String[] array = {"aa", "bb", "cc", "dd"};
        String join = ArrayKit.join(array, ",", "[", "]");
        Assertions.assertEquals("[aa],[bb],[cc],[dd]", join);
    }

    @Test
    public void getArrayTypeTest() {
        Class<?> arrayType = ArrayKit.getArrayType(int.class);
        Assertions.assertEquals(int[].class, arrayType);

        arrayType = ArrayKit.getArrayType(String.class);
        Assertions.assertEquals(String[].class, arrayType);
    }

    @Test
    public void toStingTest() {
        int[] a = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(a));
        long[] b = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(b));
        short[] c = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(c));
        double[] d = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayKit.toString(d));
        byte[] e = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1, 3, 56, 6, 7]", ArrayKit.toString(e));
        boolean[] f = {true, false, true, true, true};
        Assertions.assertEquals("[true, false, true, true, true]", ArrayKit.toString(f));
        float[] g = {1, 3, 56, 6, 7};
        Assertions.assertEquals("[1.0, 3.0, 56.0, 6.0, 7.0]", ArrayKit.toString(g));
        char[] h = {'a', 'b', '你', '好', '1'};
        Assertions.assertEquals("[a, b, 你, 好, 1]", ArrayKit.toString(h));

        String[] array = {"aa", "bb", "cc", "dd", "bb", "dd"};
        Assertions.assertEquals("[aa, bb, cc, dd, bb, dd]", ArrayKit.toString(array));
    }

}
