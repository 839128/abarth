package org.miaixz.bus.crypto.builtin.asymmetric;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Algorithm;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.MapKit;
import org.miaixz.bus.crypto.Builder;
import org.miaixz.bus.crypto.center.Sign;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名单元测试
 */
public class SignTest {

    @Test
    public void signAndVerifyUseKeyTest() {
        final String content = "我是Hanley.";

        final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ4fG8vJ0tzu7tjXMSJhyNjlE5B7GkTKMKEQlR6LY3IhIhMFVjuA6W+DqH1VMxl9h3GIM4yCKG2VRZEYEPazgVxa5/ifO8W0pfmrzWCPrddUq4t0Slz5u2lLKymLpPjCzboHoDb8VlF+1HOxjKQckAXq9q7U7dV5VxOzJDuZXlz3AgMBAAECgYABo2LfVqT3owYYewpIR+kTzjPIsG3SPqIIWSqiWWFbYlp/BfQhw7EndZ6+Ra602ecYVwfpscOHdx90ZGJwm+WAMkKT4HiWYwyb0ZqQzRBGYDHFjPpfCBxrzSIJ3QL+B8c8YHq4HaLKRKmq7VUF1gtyWaek87rETWAmQoGjt8DyAQJBAOG4OxsT901zjfxrgKwCv6fV8wGXrNfDSViP1t9r3u6tRPsE6Gli0dfMyzxwENDTI75sOEAfyu6xBlemQGmNsfcCQQCzVWQkl9YUoVDWEitvI5MpkvVKYsFLRXKvLfyxLcY3LxpLKBcEeJ/n5wLxjH0GorhJMmM2Rw3hkjUTJCoqqe0BAkATt8FKC0N2O5ryqv1xiUfuxGzW/cX2jzOwDdiqacTuuqok93fKBPzpyhUS8YM2iss7jj6Xs29JzKMOMxK7ZcpfAkAf21lwzrAu9gEgJhYlJhKsXfjJAAYKUwnuaKLs7o65mtp242ZDWxI85eK1+hjzptBJ4HOTXsfufESFY/VBovIBAkAltO886qQRoNSc0OsVlCi4X1DGo6x2RqQ9EsWPrxWEZGYuyEdODrc54b8L+zaUJLfMJdsCIHEUbM7WXxvFVXNv";
        Sign sign = Builder.sign(Algorithm.SHA1WITHRSA, privateKey, null);
        Assertions.assertNull(sign.getPublicKeyBase64());
        // 签名
        final byte[] signed = sign.sign(content.getBytes());

        final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeHxvLydLc7u7Y1zEiYcjY5ROQexpEyjChEJUei2NyISITBVY7gOlvg6h9VTMZfYdxiDOMgihtlUWRGBD2s4FcWuf4nzvFtKX5q81gj63XVKuLdEpc+btpSyspi6T4ws26B6A2/FZRftRzsYykHJAF6vau1O3VeVcTsyQ7mV5c9wIDAQAB";
        sign = Builder.sign(Algorithm.SHA1WITHRSA, null, publicKey);
        // 验证签名
        final boolean verify = sign.verify(content.getBytes(), signed);
        Assertions.assertTrue(verify);
    }

    @Test
    public void signAndVerifyTest() {
        signAndVerify(Algorithm.NONEWITHRSA);
        signAndVerify(Algorithm.MD2WITHRSA);
        signAndVerify(Algorithm.MD5withRSA);

        signAndVerify(Algorithm.SHA1WITHRSA);
        signAndVerify(Algorithm.SHA256WITHRSA);
        signAndVerify(Algorithm.SHA384WITHRSA);
        signAndVerify(Algorithm.SHA512WITHRSA);

        signAndVerify(Algorithm.NONEWITHDSA);
        signAndVerify(Algorithm.SHA1WITHDSA);

        signAndVerify(Algorithm.NONEWITHECDSA);
        signAndVerify(Algorithm.SHA1WITHECDSA);
        signAndVerify(Algorithm.SHA1WITHECDSA);
        signAndVerify(Algorithm.SHA256WITHECDSA);
        signAndVerify(Algorithm.SHA384WITHECDSA);
        signAndVerify(Algorithm.SHA512WITHECDSA);
    }

    /**
     * 测试各种算法的签名和验证签名
     *
     * @param Algorithm 算法
     */
    private void signAndVerify(final Algorithm Algorithm) {
        final byte[] data = ByteKit.toBytes("我是一段测试ab");
        final Sign sign = Builder.sign(Algorithm);

        // 签名
        final byte[] signed = sign.sign(data);

        // 验证签名
        final boolean verify = sign.verify(data, signed);
        Assertions.assertTrue(verify);
    }

    /**
     * 测试MD5withRSA算法的签名和验证签名
     */
    @Test
    public void signAndVerifyTest2() {
        final String str = "wx2421b1c4370ec43b 支付测试 JSAPI支付测试 10000100 1add1a30ac87aa2db72f57a2375d8fec http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 1415659990 14.23.150.211 1 JSAPI 0CB01533B8C1EF103065174F50BCA001";
        final byte[] data = ByteKit.toBytes(str);
        final Sign sign = Builder.sign(Algorithm.MD5withRSA);

        // 签名
        final byte[] signed = sign.sign(data);

        // 验证签名
        final boolean verify = sign.verify(data, signed);
        Assertions.assertTrue(verify);
    }

    @Test
    public void signParamsTest() {
        final Map<String, String> build = MapKit.builder(new HashMap<String, String>())
                .put("key1", "value1")
                .put("key2", "value2").build();

        final String sign1 = Builder.signParamsSha1(build);
        Assertions.assertEquals("9ed30bfe2efbc7038a824b6c55c24a11bfc0dce5", sign1);
        final String sign2 = Builder.signParamsSha1(build, "12345678");
        Assertions.assertEquals("944b68d94c952ec178c4caf16b9416b6661f7720", sign2);
        final String sign3 = Builder.signParamsSha1(build, "12345678", "abc");
        Assertions.assertEquals("edee1b477af1b96ebd20fdf08d818f352928d25d", sign3);
    }

    /**
     * 测试MD5withRSA算法的签名和验证签名
     */
    @Test
    public void signAndVerifyPSSTest() {
        final String str = "wx2421b1c4370ec43b 支付测试 JSAPI支付测试 10000100 1add1a30ac87aa2db72f57a2375d8fec http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 1415659990 14.23.150.211 1 JSAPI 0CB01533B8C1EF103065174F50BCA001";
        final byte[] data = ByteKit.toBytes(str);
        final Sign sign = Builder.sign(Algorithm.SHA256WITHRSA_PSS);

        // 签名
        final byte[] signed = sign.sign(data);

        // 验证签名
        final boolean verify = sign.verify(data, signed);
        Assertions.assertTrue(verify);
    }

}
