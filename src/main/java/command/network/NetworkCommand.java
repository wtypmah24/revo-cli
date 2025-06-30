package command.network;

import handler.network.NetworkCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "network-command", description = "Show network interfaces information")
public class NetworkCommand implements Runnable {

  private final NetworkCommandHandler handler = new NetworkCommandHandler();

  @Option(names = "--basic", description = "Show basic interface info (name, MAC, speed)")
  private boolean showBasic;

  @Option(names = "--ip", description = "Show IPv4 and IPv6 addresses")
  private boolean showIP;

  @Option(names = "--traffic", description = "Show bytes/packets sent and received")
  private boolean showTraffic;

  @Option(names = "--status", description = "Show interface status, connector, MTU")
  private boolean showStatus;

  @Override
  public void run() {
    EnumSet<NetworkOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showBasic, NetworkOutputOption.BASIC_INFO),
                Map.entry(showIP, NetworkOutputOption.IP_INFO),
                Map.entry(showTraffic, NetworkOutputOption.TRAFFIC),
                Map.entry(showStatus, NetworkOutputOption.STATUS))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(NetworkOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
