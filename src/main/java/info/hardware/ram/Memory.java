package info.hardware.ram;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class Memory {
  private final GlobalMemory memory;

  public Memory() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    this.memory = hal.getMemory();
  }

  public long totalMemory() {
    return memory.getTotal();
  }

  public long availableMemory() {
    return memory.getAvailable();
  }

  public long swapTotal() {
    return memory.getVirtualMemory().getSwapTotal();
  }

  public long swapUsed() {
    return memory.getVirtualMemory().getSwapUsed();
  }

  public long pageSize() {
    return memory.getPageSize();
  }
}
