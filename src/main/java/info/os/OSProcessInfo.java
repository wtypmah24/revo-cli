package info.os;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OSProcessInfo {
  private final List<OSProcess> processes;

  public OSProcessInfo() {
    SystemInfo si = new SystemInfo();
    OperatingSystem os = si.getOperatingSystem();
    this.processes = os.getProcesses(null, Comparator.comparingInt(OSProcess::getProcessID), 0);
  }

  public List<Integer> getProcessIds() {
    return processes.stream().map(OSProcess::getProcessID).collect(Collectors.toList());
  }

  public List<String> getProcessNames() {
    return processes.stream().map(OSProcess::getName).collect(Collectors.toList());
  }

  public List<String> getCommandLines() {
    return processes.stream().map(OSProcess::getCommandLine).collect(Collectors.toList());
  }

  public List<String> getUsers() {
    return processes.stream().map(OSProcess::getUser).collect(Collectors.toList());
  }

  public List<Long> getVirtualMemory() {
    return processes.stream().map(OSProcess::getVirtualSize).collect(Collectors.toList());
  }

  public List<Long> getResidentSetSize() {
    return processes.stream().map(OSProcess::getResidentSetSize).collect(Collectors.toList());
  }

  public List<String> getStates() {
    return processes.stream().map(p -> p.getState().name()).collect(Collectors.toList());
  }

  public List<Integer> getParentProcessIds() {
    return processes.stream().map(OSProcess::getParentProcessID).collect(Collectors.toList());
  }

  public List<Long> getUpTimes() {
    return processes.stream().map(OSProcess::getUpTime).collect(Collectors.toList());
  }
}
