package info.hardware.usb;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.UsbDevice;

import java.util.List;

public class Usb {
    private final List<UsbDevice> usbDevices;

    public Usb() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        this.usbDevices = hal.getUsbDevices(false);
    }

    public List<String> getNames() {
        return usbDevices.stream().map(UsbDevice::getName).toList();
    }

    public List<String> getVendors() {
        return usbDevices.stream().map(UsbDevice::getVendor).toList();
    }

    public List<String> getSerialNumbers() {
        return usbDevices.stream().map(UsbDevice::getSerialNumber).toList();
    }

    public List<String> getDeviceIds() {
        return usbDevices.stream().map(UsbDevice::getUniqueDeviceId).toList();
    }

    public List<String> getProductIds() {
        return usbDevices.stream().map(UsbDevice::getProductId).toList();
    }

    public List<String> getVendorIds() {
        return usbDevices.stream().map(UsbDevice::getVendorId).toList();
    }

    public List<Integer> getConnectedDevicesCount() {
        return usbDevices.stream()
                .map(d -> d.getConnectedDevices().size())
                .toList();
    }
}
