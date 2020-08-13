package org.aoju.bus.health.hardware;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.hardware.UsbDevice;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test USB device
 */
public class UsbDeviceTest {

    /**
     * Test USB Devices
     */
    @Test
    public void testUsbDevices() {
        List<UsbDevice> usbArray = Builder.getHardware().getUsbDevices(true);
        for (UsbDevice usb : usbArray) {
            assertNotNull(usb);
            testUsbRecursive(usb);
        }
    }

    private void testUsbRecursive(UsbDevice usb) {
        assertTrue(usb.getName().length() > 0);
        assertNotNull(usb.getVendor());
        assertNotNull(usb.getProductId());
        assertNotNull(usb.getVendorId());
        assertNotNull(usb.getSerialNumber());

        for (UsbDevice nested : usb.getConnectedDevices()) {
            testUsbRecursive(nested);
        }
    }

}
