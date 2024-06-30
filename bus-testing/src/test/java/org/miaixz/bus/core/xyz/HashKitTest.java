package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteOrder;

public class HashKitTest {

    @Test
    public void cityHash128Test() {
        final String s = "Google发布的Hash计算算法：CityHash64 与 CityHash128";
        final long[] hash = HashKit.cityHash128(ByteKit.toBytes(s)).getLongArray(ByteOrder.BIG_ENDIAN);
        Assertions.assertEquals(0x5944f1e788a18db0L, hash[0]);
        Assertions.assertEquals(0xc2f68d8b2bf4a5cfL, hash[1]);
    }

    @Test
    public void cityHash64Test() {
        final String s = "Google发布的Hash计算算法：CityHash64 与 CityHash128";
        final long hash = HashKit.cityHash64(ByteKit.toBytes(s));
        Assertions.assertEquals(0x1d408f2bbf967e2aL, hash);
    }

    @Test
    public void cityHash32Test() {
        final String s = "Google发布的Hash计算算法：CityHash64 与 CityHash128";
        final int hash = HashKit.cityHash32(ByteKit.toBytes(s));
        Assertions.assertEquals(0xa8944fbe, hash);
    }

}
