package info.hardware.network;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.util.List;

public class Network {
  private final List<NetworkIF> networkInterfaces;

  public Network() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    this.networkInterfaces = hal.getNetworkIFs();
    for (NetworkIF nif : networkInterfaces) {
      nif.updateAttributes();
    }
  }

  public List<String> getNames() {
    return networkInterfaces.stream().map(NetworkIF::getName).toList();
  }

  public List<String> getDisplayNames() {
    return networkInterfaces.stream().map(NetworkIF::getDisplayName).toList();
  }

  public List<String> getMacAddresses() {
    return networkInterfaces.stream().map(NetworkIF::getMacaddr).toList();
  }

  public List<List<String>> getIPv4Addresses() {
    return networkInterfaces.stream().map(iface -> List.of(iface.getIPv4addr())).toList();
  }

  public List<List<String>> getIPv6Addresses() {
    return networkInterfaces.stream().map(iface -> List.of(iface.getIPv6addr())).toList();
  }

  public List<Long> getBytesSent() {
    return networkInterfaces.stream().map(NetworkIF::getBytesSent).toList();
  }

  public List<Long> getBytesRecv() {
    return networkInterfaces.stream().map(NetworkIF::getBytesRecv).toList();
  }

  public List<Long> getPacketsSent() {
    return networkInterfaces.stream().map(NetworkIF::getPacketsSent).toList();
  }

  public List<Long> getPacketsRecv() {
    return networkInterfaces.stream().map(NetworkIF::getPacketsRecv).toList();
  }

  public List<String> getInterfaceStatuses() {
    return networkInterfaces.stream().map(nif -> nif.getIfOperStatus().name()).toList();
  }

  public List<Long> getMTUs() {
    return networkInterfaces.stream().map(NetworkIF::getMTU).toList();
  }

  public List<String> getSpeedMbit() {
    return networkInterfaces.stream()
        .map(nif -> nif.getSpeed() > 0 ? (nif.getSpeed() / 1_000_000) + " Mbps" : "Unknown")
        .toList();
  }

  public List<Boolean> isConnectorPresent() {
    return networkInterfaces.stream().map(NetworkIF::isConnectorPresent).toList();
  }
}
