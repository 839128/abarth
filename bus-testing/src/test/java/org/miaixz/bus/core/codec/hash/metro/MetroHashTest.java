package org.miaixz.bus.core.codec.hash.metro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.codec.hash.CityHash;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.HexKit;
import org.miaixz.bus.core.xyz.RandomKit;

public class MetroHashTest {

    private static String[] getRandomStringArray() {
        final String[] result = new String[10000000];
        int index = 0;
        while (index < 10000000) {
            result[index++] = RandomKit.randomStringLower(RandomKit.randomInt(64));
        }
        return result;
    }

    @Test
    public void testEmpty() {
        Assertions.assertEquals("705fb008071e967d", HexKit.toHex(MetroHash64.of(0).hash64(ByteKit.toBytes(""))));
    }

    @Test
    public void test1Low() {
        Assertions.assertEquals("AF6F242B7ED32BCB", h64("a"));
    }

    @Test
    public void test1High() {
        Assertions.assertEquals("D51BA21D219C37B3", h64("é"));
    }

    @Test
    public void metroHash64Test() {
        final byte[] str = "我是一段测试123".getBytes(Charset.UTF_8);
        final long hash64 = MetroHash64.of(0).hash64(str);
        Assertions.assertEquals(147395857347476456L, hash64);
    }

    /**
     * 数据量越大 MetroHash 优势越明显，
     */
    @Test
    @Disabled
    public void bulkHashing64Test() {
        final String[] strArray = getRandomStringArray();
        final long startCity = System.currentTimeMillis();
        for (final String s : strArray) {
            CityHash.INSTANCE.hash64(s.getBytes());
        }
        final long endCity = System.currentTimeMillis();

        final long startMetro = System.currentTimeMillis();
        for (final String s : strArray) {
            MetroHash64.of(0).hash64(ByteKit.toBytes(s));
        }
        final long endMetro = System.currentTimeMillis();

        System.out.println("metroHash =============" + (endMetro - startMetro));
        System.out.println("cityHash =============" + (endCity - startCity));
    }

    /**
     * 数据量越大 MetroHash 优势越明显，
     */
    @Test
    @Disabled
    public void bulkHashing128Test() {
        final String[] strArray = getRandomStringArray();
        final long startCity = System.currentTimeMillis();
        for (final String s : strArray) {
            CityHash.INSTANCE.hash128(s.getBytes());
        }
        final long endCity = System.currentTimeMillis();

        final long startMetro = System.currentTimeMillis();
        for (final String s : strArray) {
            MetroHash128.of(0).hash128(ByteKit.toBytes(s));
        }
        final long endMetro = System.currentTimeMillis();

        System.out.println("metroHash =============" + (endMetro - startMetro));
        System.out.println("cityHash =============" + (endCity - startCity));
    }

    private String h64(final String value) {
        return HexKit.toHex(MetroHash64.of(0).hash64(ByteKit.toBytes(value))).toUpperCase();
    }

}
