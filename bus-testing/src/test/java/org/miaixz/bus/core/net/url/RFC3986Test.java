package org.miaixz.bus.core.net.url;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.Charset;

public class RFC3986Test {

    @Test
    public void pacharTest() {
        final String encode = RFC3986.PCHAR.encode("=", Charset.UTF_8);
        Assertions.assertEquals("=", encode);
    }

    @Test
    public void encodeQueryTest() {
        String encode = RFC3986.QUERY_PARAM_VALUE.encode("a=b", Charset.UTF_8);
        Assertions.assertEquals("a=b", encode);

        encode = RFC3986.QUERY_PARAM_VALUE.encode("a+1=b", Charset.UTF_8);
        Assertions.assertEquals("a+1=b", encode);
    }

    @Test
    public void encodeQueryPercentTest() {
        final String encode = RFC3986.QUERY_PARAM_VALUE.encode("a=%b", Charset.UTF_8);
        Assertions.assertEquals("a=%25b", encode);
    }

    @Test
    public void encodeQueryWithSafeTest() {
        final String encode = RFC3986.QUERY_PARAM_VALUE.encode("a=%25", Charset.UTF_8, '%');
        Assertions.assertEquals("a=%25", encode);
    }

}
