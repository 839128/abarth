package org.miaixz.bus.cron.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.CrontabException;

public class CronPatternBuilderTest {

    @Test
    public void buildMatchAllTest() {
        String build = CronPatternBuilder.of().build();
        Assertions.assertEquals("* * * * *", build);

        build = CronPatternBuilder.of()
                .set(Part.SECOND, "*")
                .build();
        Assertions.assertEquals("* * * * * *", build);

        build = CronPatternBuilder.of()
                .set(Part.SECOND, "*")
                .set(Part.YEAR, "*")
                .build();
        Assertions.assertEquals("* * * * * * *", build);
    }

    @Test
    public void buildRangeTest() {
        final String build = CronPatternBuilder.of()
                .set(Part.SECOND, "*")
                .setRange(Part.HOUR, 2, 9)
                .build();
        Assertions.assertEquals("* * 2-9 * * *", build);
    }

    @Test
    public void buildRangeErrorTest() {
        Assertions.assertThrows(CrontabException.class, () -> {
            final String build = CronPatternBuilder.of()
                    .set(Part.SECOND, "*")
                    // 55无效值
                    .setRange(Part.HOUR, 2, 55)
                    .build();
            Assertions.assertEquals("* * 2-9 * * *", build);
        });
    }

}
