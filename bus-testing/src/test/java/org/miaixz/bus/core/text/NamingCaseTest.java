package org.miaixz.bus.core.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.center.map.Dictionary;
import org.miaixz.bus.core.lang.Symbol;

public class NamingCaseTest {

    @Test
    public void toCamelCaseTest() {
        Dictionary.of()
                .set("Table_Test_Of_day", "tableTestOfDay")
                .set("TableTestOfDay", "tableTestOfDay")
                .set("abc_1d", "abc1d")
                .forEach((key, value) -> Assertions.assertEquals(value, NamingCase.toCamelCase(key)));
    }

    @Test
    public void toCamelCaseFromDashedTest() {
        Dictionary.of()
                .set("Table-Test-Of-day", "tableTestOfDay")
                .forEach((key, value) -> Assertions.assertEquals(value, NamingCase.toCamelCase(key, Symbol.C_MINUS)));
    }

    @Test
    public void toUnderLineCaseTest() {
        Dictionary.of()
                .set("Table_Test_Of_day", "table_test_of_day")
                .set("_Table_Test_Of_day_", "_table_test_of_day_")
                .set("_Table_Test_Of_DAY_", "_table_test_of_DAY_")
                .set("_TableTestOfDAYToday", "_table_test_of_DAY_today")
                .set("HelloWorld_test", "hello_world_test")
                .set("H2", "H2")
                .set("H#case", "H#case")
                .set("PNLabel", "PN_label")
                .set("wPRunOZTime", "w_P_run_OZ_time")
                .set("customerNickV2", "customer_nick_v2")
                .set("DEPT_NAME", "DEPT_NAME")
                .forEach((key, value) -> Assertions.assertEquals(value, NamingCase.toUnderlineCase(key)));
    }

    @Test
    public void issueTest() {
        Assertions.assertEquals("t1C1", NamingCase.toUnderlineCase("t1C1"));
    }

    @Test
    public void issueTest1() {
        String camelCase = NamingCase.toCamelCase("user_name,BIRTHDAY");
        Assertions.assertEquals("userName,birthday", camelCase);

        camelCase = NamingCase.toCamelCase("user_name,BIRTHDAY", '_', false);
        Assertions.assertEquals("userName,BIRTHDAY", camelCase);
    }

}
