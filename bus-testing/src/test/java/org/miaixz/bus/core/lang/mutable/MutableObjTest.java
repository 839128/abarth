package org.miaixz.bus.core.lang.mutable;

/**
 * test for {@link MutableObject}
 */
class MutableObjTest extends BaseMutableTest<String, MutableObject<String>> {

    /**
     * 创建一个值
     *
     * @return 值
     */
    @Override
    String getValue1() {
        return "test1";
    }

    /**
     * 创建一个值
     *
     * @return 值
     */
    @Override
    String getValue2() {
        return "test2";
    }

    /**
     * 创建一个{@link Mutable}
     *
     * @param value 值
     * @return 值
     */
    @Override
    MutableObject<String> getMutable(String value) {
        return new MutableObject<>(value);
    }
}
