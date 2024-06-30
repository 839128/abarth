package org.miaixz.bus.core.xyz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.TimeZone;

public class ZoneKitTest {

    @Test
    public void test() {
        Assertions.assertEquals(ZoneId.systemDefault(), ZoneKit.toZoneId(null));
        Assertions.assertEquals(TimeZone.getDefault(), ZoneKit.toTimeZone(null));
    }

}
