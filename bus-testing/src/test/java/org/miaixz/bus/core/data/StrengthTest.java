package org.miaixz.bus.core.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrengthTest {

    @Test
    public void strengthTest() {
        final String passwd = "2hAj5#mne-ix.86H";
        Assertions.assertEquals(13, Strength.check(passwd));
    }

    @Test
    public void strengthNumberTest() {
        final String passwd = "9999999999999";
        Assertions.assertEquals(0, Strength.check(passwd));
    }

}
