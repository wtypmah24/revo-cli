package info.os;

import oshi.SystemInfo;
import oshi.software.os.OSService;
import oshi.software.os.OperatingSystem;

import java.util.List;
import java.util.stream.Collectors;

public class OSServiceInfo {
  private final List<OSService> services;

  public OSServiceInfo() {
    SystemInfo si = new SystemInfo();
    OperatingSystem os = si.getOperatingSystem();
    this.services = os.getServices();
  }

  public List<String> getNames() {
    return services.stream().map(OSService::getName).collect(Collectors.toList());
  }

  public List<String> getStates() {
    return services.stream().map(service -> service.getState().name()).collect(Collectors.toList());
  }

  public List<Integer> getProcessIds() {
    return services.stream().map(OSService::getProcessID).collect(Collectors.toList());
  }
}
