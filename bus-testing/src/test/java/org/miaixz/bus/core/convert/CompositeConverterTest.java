package org.miaixz.bus.core.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

/**
 * ConverterRegistry 单元测试
 */
public class CompositeConverterTest {

    @Test
    public void getConverterTest() {
        final Converter converter = CompositeConverter.getInstance().getConverter(CharSequence.class, false);
        Assertions.assertNotNull(converter);
    }

    @Test
    public void customTest() {
        final int a = 454553;
        final CompositeConverter compositeConverter = CompositeConverter.getInstance();

        CharSequence result = (CharSequence) compositeConverter.convert(CharSequence.class, a);
        Assertions.assertEquals("454553", result);

        //此处做为示例自定义CharSequence转换，因为已经提供CharSequence转换，请尽量不要替换
        //替换可能引发关联转换异常（例如覆盖CharSequence转换会影响全局）
        compositeConverter.putCustom(CharSequence.class, new CustomConverter());
        result = (CharSequence) compositeConverter.convert(CharSequence.class, a);
        Assertions.assertEquals("Custom: 454553", result);
    }

    public static class CustomConverter implements Converter {
        @Override
        public Object convert(final Type targetType, final Object value) {
            return "Custom: " + value.toString();
        }
    }

}
