package org.miaixz.bus.health.builtin.hardware;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test USB device
 */
class UsbDeviceTest {
    /**
     * Test USB Devices
     */
    @Test
    void testUsbDevices() {
        Platform si = new Platform();
        List<UsbDevice> usbList = si.getHardware().getUsbDevices(true);
        for (UsbDevice usb : usbList) {
            assertThat("USB object shouldn't be null", usb, is(notNullValue()));
            testUsbRecursive(usb);
        }
    }

    private void testUsbRecursive(UsbDevice usb) {
        assertThat("USB name shouldn't be blank", usb.getName(), is(not(emptyString())));
        assertThat("USB vendor shouldn't be null", usb.getVendor(), is(notNullValue()));
        assertThat("USB product ID shouldn't be null", usb.getProductId(), is(notNullValue()));
        assertThat("USB vendor ID shouldn't be null", usb.getVendorId(), is(notNullValue()));
        assertThat("USB serial number shouldn't be null", usb.getSerialNumber(), is(notNullValue()));

        for (UsbDevice nested : usb.getConnectedDevices()) {
            testUsbRecursive(nested);
        }
    }

}
