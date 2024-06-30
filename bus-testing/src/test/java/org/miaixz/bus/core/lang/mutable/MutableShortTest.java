package org.miaixz.bus.core.lang.mutable;

/**
 *
 */
public class MutableShortTest extends BaseMutableTest<Number, MutableShort> {
    /**
     * 创建一个值，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    Number getValue1() {
        return Short.MAX_VALUE;
    }

    /**
     * 创建一个值，与{@link #getValue1()}不同，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    Number getValue2() {
        return Short.MIN_VALUE;
    }

    /**
     * 创建一个{@link Mutable}
     *
     * @param value 值
     * @return 值
     */
    @Override
    MutableShort getMutable(Number value) {
        return new MutableShort(value);
    }
}
