package org.aoju.bus.crypto;

import org.aoju.bus.core.lang.Algorithm;
import org.aoju.bus.core.utils.MapUtils;
import org.aoju.bus.core.utils.StringUtils;
import org.aoju.bus.crypto.algorithm.asymmetric.Sign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名单元测试
 */
public class SignTest {

    @Test
    public void signAndVerifyUseKeyTest() {
        String content = "我是Hanley.";

        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ4fG8vJ0tzu7tjXMSJhyNjlE5B7GkTKMKEQlR6LY3IhIhMFVjuA6W+DqH1VMxl9h3GIM4yCKG2VRZEYEPazgVxa5/ifO8W0pfmrzWCPrddUq4t0Slz5u2lLKymLpPjCzboHoDb8VlF+1HOxjKQckAXq9q7U7dV5VxOzJDuZXlz3AgMBAAECgYABo2LfVqT3owYYewpIR+kTzjPIsG3SPqIIWSqiWWFbYlp/BfQhw7EndZ6+Ra602ecYVwfpscOHdx90ZGJwm+WAMkKT4HiWYwyb0ZqQzRBGYDHFjPpfCBxrzSIJ3QL+B8c8YHq4HaLKRKmq7VUF1gtyWaek87rETWAmQoGjt8DyAQJBAOG4OxsT901zjfxrgKwCv6fV8wGXrNfDSViP1t9r3u6tRPsE6Gli0dfMyzxwENDTI75sOEAfyu6xBlemQGmNsfcCQQCzVWQkl9YUoVDWEitvI5MpkvVKYsFLRXKvLfyxLcY3LxpLKBcEeJ/n5wLxjH0GorhJMmM2Rw3hkjUTJCoqqe0BAkATt8FKC0N2O5ryqv1xiUfuxGzW/cX2jzOwDdiqacTuuqok93fKBPzpyhUS8YM2iss7jj6Xs29JzKMOMxK7ZcpfAkAf21lwzrAu9gEgJhYlJhKsXfjJAAYKUwnuaKLs7o65mtp242ZDWxI85eK1+hjzptBJ4HOTXsfufESFY/VBovIBAkAltO886qQRoNSc0OsVlCi4X1DGo6x2RqQ9EsWPrxWEZGYuyEdODrc54b8L+zaUJLfMJdsCIHEUbM7WXxvFVXNv";
        Sign sign = Builder.sign(Algorithm.SHA1withRSA, privateKey, null);
        Assertions.assertNull(sign.getPublicKeyBase64());
        // 签名
        byte[] signed = sign.sign(content.getBytes());

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeHxvLydLc7u7Y1zEiYcjY5ROQexpEyjChEJUei2NyISITBVY7gOlvg6h9VTMZfYdxiDOMgihtlUWRGBD2s4FcWuf4nzvFtKX5q81gj63XVKuLdEpc+btpSyspi6T4ws26B6A2/FZRftRzsYykHJAF6vau1O3VeVcTsyQ7mV5c9wIDAQAB";
        sign = Builder.sign(Algorithm.SHA1withRSA, null, publicKey);
        // 验证签名
        boolean verify = sign.verify(content.getBytes(), signed);
        Assertions.assertTrue(verify);
    }

    @Test
    public void signAndVerifyTest() {
        signAndVerify(Algorithm.NONEwithRSA);
        signAndVerify(Algorithm.MD2withRSA);
        signAndVerify(Algorithm.MD5withRSA);

        signAndVerify(Algorithm.SHA1withRSA);
        signAndVerify(Algorithm.SHA256withRSA);
        signAndVerify(Algorithm.SHA384withRSA);
        signAndVerify(Algorithm.SHA512withRSA);

        signAndVerify(Algorithm.NONEwithDSA);
        signAndVerify(Algorithm.SHA1withDSA);

        signAndVerify(Algorithm.NONEwithECDSA);
        signAndVerify(Algorithm.SHA1withECDSA);
        signAndVerify(Algorithm.SHA1withECDSA);
        signAndVerify(Algorithm.SHA256withECDSA);
        signAndVerify(Algorithm.SHA384withECDSA);
        signAndVerify(Algorithm.SHA512withECDSA);
    }

    /**
     * 测试各种算法的签名和验证签名
     *
     * @param Algorithm 算法
     */
    private void signAndVerify(String Algorithm) {
        byte[] data = StringUtils.bytes("我是一段测试ab");
        Sign sign = Builder.sign(Algorithm);

        // 签名
        byte[] signed = sign.sign(data);

        // 验证签名
        boolean verify = sign.verify(data, signed);
        Assertions.assertTrue(verify);
    }

    /**
     * 测试MD5withRSA算法的签名和验证签名
     */
    @Test
    public void signAndVerify2() {
        String str = "wx2421b1c4370ec43b 支付测试 JSAPI支付测试 10000100 1add1a30ac87aa2db72f57a2375d8fec http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 1415659990 14.23.150.211 1 JSAPI 0CB01533B8C1EF103065174F50BCA001";
        byte[] data = StringUtils.bytes(str);
        Sign sign = Builder.sign(Algorithm.MD5withRSA);

        // 签名
        byte[] signed = sign.sign(data);

        // 验证签名
        boolean verify = sign.verify(data, signed);
        Assertions.assertTrue(verify);
    }

    @Test
    public void signParamsTest() {
        Map<String, String> build = MapUtils.builder(new HashMap<String, String>())
                .put("key1", "value1")
                .put("key2", "value2").build();

        String sign = Builder.signParamsSha1(build);
        Assertions.assertEquals("9ed30bfe2efbc7038a824b6c55c24a11bfc0dce5", sign);

    }

}
