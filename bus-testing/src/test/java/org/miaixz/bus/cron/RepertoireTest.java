package org.miaixz.bus.cron;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.data.id.ID;
import org.miaixz.bus.core.lang.Console;
import org.miaixz.bus.cron.pattern.CronPattern;

import java.util.List;

public class RepertoireTest {

    @Test
    public void taskTableTest() {
        final Repertoire taskTable = new Repertoire();
        taskTable.add(ID.fastUUID(), new CronPattern("*/10 * * * * *"), () -> Console.log("Task 1"));
        taskTable.add(ID.fastUUID(), new CronPattern("*/20 * * * * *"), () -> Console.log("Task 2"));
        taskTable.add(ID.fastUUID(), new CronPattern("*/30 * * * * *"), () -> Console.log("Task 3"));

        Assertions.assertEquals(3, taskTable.size());
        final List<String> ids = taskTable.getIds();
        Assertions.assertEquals(3, ids.size());

        // getById
        Assertions.assertEquals(new CronPattern("*/10 * * * * *"), taskTable.getPattern(ids.get(0)));
        Assertions.assertEquals(new CronPattern("*/20 * * * * *"), taskTable.getPattern(ids.get(1)));
        Assertions.assertEquals(new CronPattern("*/30 * * * * *"), taskTable.getPattern(ids.get(2)));

        // set test
        taskTable.updatePattern(ids.get(2), new CronPattern("*/40 * * * * *"));
        Assertions.assertEquals(new CronPattern("*/40 * * * * *"), taskTable.getPattern(ids.get(2)));

        // getTask
        Assertions.assertEquals(
                taskTable.getTask(1),
                taskTable.getTask(ids.get(1))
        );
    }

}
