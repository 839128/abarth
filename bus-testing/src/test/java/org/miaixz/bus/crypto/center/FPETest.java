package org.miaixz.bus.crypto.center;

import org.bouncycastle.crypto.util.BasicAlphabetMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.RandomKit;

public class FPETest {

    @Test
    public void ff1Test() {
        // 映射字符表，规定了明文和密文的字符范围
        final BasicAlphabetMapper numberMapper = new BasicAlphabetMapper("A0123456789");
        // 初始化 aes 密钥
        final byte[] keyBytes = RandomKit.randomBytes(16);
        final FPE fpe = new FPE(FPE.FPEMode.FF1, keyBytes, numberMapper, null);
        // 原始数据
        final String phone = RandomKit.randomString("A0123456789", 13);
        final String encrypt = fpe.encrypt(phone);
        // 加密后与原密文长度一致
        Assertions.assertEquals(phone.length(), encrypt.length());

        final String decrypt = fpe.decrypt(encrypt);
        Assertions.assertEquals(phone, decrypt);
    }

    @Test
    public void ff3Test() {
        // 映射字符表，规定了明文和密文的字符范围
        final BasicAlphabetMapper numberMapper = new BasicAlphabetMapper("A0123456789");
        // 初始化 aes 密钥
        final byte[] keyBytes = RandomKit.randomBytes(16);
        final FPE fpe = new FPE(FPE.FPEMode.FF3_1, keyBytes, numberMapper, null);

        // 原始数据
        final String phone = RandomKit.randomString("A0123456789", 13);
        final String encrypt = fpe.encrypt(phone);
        // 加密后与原密文长度一致
        Assertions.assertEquals(phone.length(), encrypt.length());

        final String decrypt = fpe.decrypt(encrypt);
        Assertions.assertEquals(phone, decrypt);
    }

}
