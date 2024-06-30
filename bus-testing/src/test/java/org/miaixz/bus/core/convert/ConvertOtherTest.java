package org.miaixz.bus.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

import java.util.concurrent.TimeUnit;

/**
 * 其它转换
 */
public class ConvertOtherTest {

    @Test
    public void hexTest() {
        final String a = "我是一个小小的可爱的字符串";
        final String hex = Convert.toHex(a, Charset.UTF_8);
        Assertions.assertEquals("e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2", hex);

        final String raw = Convert.hexToString(hex, Charset.UTF_8);
        Assertions.assertEquals(a, raw);
    }

    @Test
    public void unicodeTest() {
        final String a = "我是一个小小的可爱的字符串";

        final String unicode = Convert.strToUnicode(a);
        Assertions.assertEquals("\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32", unicode);

        final String raw = Convert.unicodeToString(unicode);
        Assertions.assertEquals(raw, a);

        // 针对有特殊空白符的Unicode
        final String str = "你 好";
        final String unicode2 = Convert.strToUnicode(str);
        Assertions.assertEquals("\\u4f60\\u00a0\\u597d", unicode2);

        final String str2 = Convert.unicodeToString(unicode2);
        Assertions.assertEquals(str, str2);
    }

    @Test
    public void convertCharsetTest() {
        final String a = "我不是乱码";
        // 转换后result为乱码
        final String result = Convert.convertCharset(a, Charset.DEFAULT_UTF_8, Charset.DEFAULT_ISO_8859_1);
        final String raw = Convert.convertCharset(result, Charset.DEFAULT_ISO_8859_1, "UTF-8");
        Assertions.assertEquals(raw, a);
    }

    @Test
    public void convertTimeTest() {
        final long a = 4535345;
        final long minutes = Convert.convertTime(a, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);
        Assertions.assertEquals(75, minutes);
    }

    @Test
    public void wrapUnwrapTest() {
        // 去包装
        final Class<?> wrapClass = Integer.class;
        final Class<?> unWraped = Convert.unWrap(wrapClass);
        Assertions.assertEquals(int.class, unWraped);

        // 包装
        final Class<?> primitiveClass = long.class;
        final Class<?> wraped = Convert.wrap(primitiveClass);
        Assertions.assertEquals(Long.class, wraped);
    }

}
