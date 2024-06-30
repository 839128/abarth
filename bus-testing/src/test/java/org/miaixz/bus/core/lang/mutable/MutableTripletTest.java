package org.miaixz.bus.core.lang.mutable;

/**
 *
 */
public class MutableTripletTest extends BaseMutableTest<MutableTriplet<String, String, String>, MutableTriplet<String, String, String>> {

    private static final MutableTriplet<String, String, String> VALUE1 = new MutableTriplet<>("1", "2", "3");
    private static final MutableTriplet<String, String, String> VALUE2 = new MutableTriplet<>("4", "5", "6");

    /**
     * 创建一个值，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    MutableTriplet<String, String, String> getValue1() {
        return VALUE1;
    }

    /**
     * 创建一个值，与{@link #getValue1()}不同，且反复调用应该返回完全相同的值
     *
     * @return 值
     */
    @Override
    MutableTriplet<String, String, String> getValue2() {
        return VALUE2;
    }

    /**
     * 创建一个{@link Mutable}
     *
     * @param value 值
     * @return 值
     */
    @Override
    MutableTriplet<String, String, String> getMutable(MutableTriplet<String, String, String> value) {
        return new MutableTriplet<>(value.getLeft(), value.getMiddle(), value.getRight());
    }

}
