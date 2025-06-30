package handler.network;

import command.network.NetworkOutputOption;
import info.hardware.network.Network;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class NetworkCommandHandler {
  private final Network network = new Network();

  private final Map<NetworkOutputOption, Runnable> actions =
      Map.of(
          NetworkOutputOption.BASIC_INFO, this::printBasicInfo,
          NetworkOutputOption.IP_INFO, this::printIPInfo,
          NetworkOutputOption.TRAFFIC, this::printTrafficStats,
          NetworkOutputOption.STATUS, this::printStatus);

  public void handle(Set<NetworkOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No network options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printBasicInfo() {
    System.out.println("🌐 Network Interfaces:");
    var names = network.getNames();
    var displayNames = network.getDisplayNames();
    var macs = network.getMacAddresses();
    var speeds = network.getSpeedMbit();

    IntStream.range(0, names.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• %s (%s) | MAC: %s | Speed: %s%n",
                  names.get(i), displayNames.get(i), macs.get(i), speeds.get(i));
            });
  }

  private void printIPInfo() {
    System.out.println("📡 IP Addresses:");
    var ipv4 = network.getIPv4Addresses();
    var ipv6 = network.getIPv6Addresses();

    IntStream.range(0, ipv4.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Interface %d: IPv4: %s | IPv6: %s%n",
                  i, String.join(", ", ipv4.get(i)), String.join(", ", ipv6.get(i)));
            });
  }

  private void printTrafficStats() {
    System.out.println("📊 Traffic Stats:");
    var sent = network.getBytesSent();
    var recv = network.getBytesRecv();
    var pktSent = network.getPacketsSent();
    var pktRecv = network.getPacketsRecv();

    IntStream.range(0, sent.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Interface %d → Sent: %.2f MB (%d pkts), Received: %.2f MB (%d pkts)%n",
                  i, sent.get(i) / 1e6, pktSent.get(i), recv.get(i) / 1e6, pktRecv.get(i));
            });
  }

  private void printStatus() {
    System.out.println("🔌 Interface Status:");
    var statuses = network.getInterfaceStatuses();
    var connectors = network.isConnectorPresent();
    var mtus = network.getMTUs();

    IntStream.range(0, statuses.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Interface %d → Status: %s | Connector: %s | MTU: %d%n",
                  i, statuses.get(i), connectors.get(i) ? "Present" : "Not Present", mtus.get(i));
            });
  }
}
