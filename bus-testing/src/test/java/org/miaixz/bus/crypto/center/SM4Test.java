package org.miaixz.bus.crypto.center;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.lang.Charset;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.Keeper;
import org.miaixz.bus.crypto.Padding;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * SM4单元测试
 */
public class SM4Test {

    private static final SM4 sm4 = new SM4();

    private static final boolean IS_CLOSE = false;

    public static void encrypt(final String source, final String target) {
        try (final InputStream input = Files.newInputStream(Paths.get(source));
             final OutputStream out = Files.newOutputStream(Paths.get(target))) {
            sm4.encrypt(input, out, IS_CLOSE);
            System.out.println("============encrypt end");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void decrypt(final String source, final String target) {
        try (final InputStream input = Files.newInputStream(Paths.get(source));
             final OutputStream out = Files.newOutputStream(Paths.get(target))) {
            sm4.decrypt(input, out, IS_CLOSE);
            System.out.println("============decrypt end");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    public void sm4Test() {
        final String source = "d:/test/sm4_1.txt";
        final String target = "d:/test/sm4_2.data";
        final String target2 = "d:/test/sm4_3.txt";
        encrypt(source, target);
        decrypt(target, target2);
    }

    @Test
    public void sm4Test1() {
        final String content = "test中文";
        final SM4 sm4 = Builder.sm4();

        final String encryptHex = sm4.encryptHex(content);
        final String decryptStr = sm4.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void sm4Test2() {
        final String content = "test中文";
        final SM4 sm4 = new SM4(Algorithm.Mode.CTR, Padding.PKCS5Padding);
        sm4.setIv("aaaabbbb".getBytes());

        final String encryptHex = sm4.encryptHex(content);
        final String decryptStr = sm4.decryptString(encryptHex, Charset.UTF_8);

        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void sm4ECBPKCS5PaddingTest2() {
        final String content = "test中文";
        final SM4 sm4 = new SM4(Algorithm.Mode.ECB, Padding.PKCS5Padding);
        Assertions.assertEquals("SM4/ECB/PKCS5Padding", sm4.getCipher().getAlgorithm());

        final String encryptHex = sm4.encryptHex(content);
        final String decryptStr = sm4.decryptString(encryptHex, Charset.UTF_8);
        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void sm4TestWithCustomKeyTest() {
        final String content = "test中文";

        final SecretKey key = Keeper.generateKey(Algorithm.SM4.getValue());

        final SM4 sm4 = new SM4(Algorithm.Mode.ECB, Padding.PKCS5Padding, key);
        Assertions.assertEquals("SM4/ECB/PKCS5Padding", sm4.getCipher().getAlgorithm());

        final String encryptHex = sm4.encryptHex(content);
        final String decryptStr = sm4.decryptString(encryptHex, Charset.UTF_8);
        Assertions.assertEquals(content, decryptStr);
    }

    @Test
    public void sm4TestWithCustomKeyTest2() {
        final String content = "test中文frfewrewrwerwer---------------------------------------------------";

        final byte[] key = Keeper.generateKey(Algorithm.SM4.getValue(), 128).getEncoded();

        final SM4 sm4 = Builder.sm4(key);
        Assertions.assertEquals("SM4", sm4.getCipher().getAlgorithm());

        final String encryptHex = sm4.encryptHex(content);
        final String decryptStr = sm4.decryptString(encryptHex, Charset.UTF_8);
        Assertions.assertEquals(content, decryptStr);
    }

}
