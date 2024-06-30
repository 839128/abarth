package org.miaixz.bus.core.lang.mutable;

import org.miaixz.bus.core.xyz.MapKit;

import java.util.Map;

/**
 *
 */
public class MutableEntryTest extends BaseMutableTest<Map.Entry<String, String>, MutableEntry<String, String>> {

    private static final Map.Entry<String, String> ENTRY1 = MapKit.entry("key1", "value1");
    private static final Map.Entry<String, String> ENTRY2 = MapKit.entry("key2", "value2");

    /**
     * 创建一个值，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    Map.Entry<String, String> getValue1() {
        return ENTRY1;
    }

    /**
     * 创建一个值，与{@link #getValue1()}不同，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    Map.Entry<String, String> getValue2() {
        return ENTRY2;
    }

    /**
     * 创建一个{@link Mutable}
     *
     * @param value 值
     * @return 值
     */
    @Override
    MutableEntry<String, String> getMutable(Map.Entry<String, String> value) {
        return new MutableEntry<>(value.getKey(), value.getValue());
    }

}
