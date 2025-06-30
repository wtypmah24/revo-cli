package handler.usb;

import command.usb.UsbOutputOption;
import info.hardware.usb.Usb;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class UsbCommandHandler {
  private final Usb usb = new Usb();

  private final Map<UsbOutputOption, Runnable> actions =
      Map.of(
          UsbOutputOption.SUMMARY, this::printUsbSummary,
          UsbOutputOption.DEVICE_IDS, this::printUsbIds,
          UsbOutputOption.CONNECTIONS, this::printConnectionStats);

  public void handle(Set<UsbOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No USB options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printUsbSummary() {
    System.out.println("🔌 USB Devices:");
    var names = usb.getNames();
    var vendors = usb.getVendors();
    var serials = usb.getSerialNumbers();

    IntStream.range(0, names.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• %s (%s), Serial: %s%n",
                  names.get(i), vendors.get(i), serials.get(i).isBlank() ? "N/A" : serials.get(i));
            });
  }

  private void printUsbIds() {
    System.out.println("🆔 USB IDs:");
    var deviceIds = usb.getDeviceIds();
    var vendorIds = usb.getVendorIds();
    var productIds = usb.getProductIds();

    IntStream.range(0, deviceIds.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• VID: %s | PID: %s | UID: %s%n",
                  vendorIds.get(i), productIds.get(i), deviceIds.get(i));
            });
  }

  private void printConnectionStats() {
    System.out.println("🔗 Connected Devices:");
    var names = usb.getNames();
    var connected = usb.getConnectedDevicesCount();

    IntStream.range(0, names.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• %s → Connected sub-devices: %d%n", names.get(i), connected.get(i));
            });
  }
}
