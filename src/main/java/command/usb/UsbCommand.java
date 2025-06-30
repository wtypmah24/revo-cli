package command.usb;

import handler.usb.UsbCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "usb-command", description = "Show USB device information")
public class UsbCommand implements Runnable {

  private final UsbCommandHandler handler = new UsbCommandHandler();

  @Option(names = "--summary", description = "Show summary of USB devices")
  private boolean showSummary;

  @Option(names = "--device-ids", description = "Show device, vendor, and product IDs")
  private boolean showDeviceIds;

  @Option(names = "--connections", description = "Show connected sub-devices count")
  private boolean showConnections;

  @Override
  public void run() {
    EnumSet<UsbOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showSummary, UsbOutputOption.SUMMARY),
                Map.entry(showDeviceIds, UsbOutputOption.DEVICE_IDS),
                Map.entry(showConnections, UsbOutputOption.CONNECTIONS))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(UsbOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
