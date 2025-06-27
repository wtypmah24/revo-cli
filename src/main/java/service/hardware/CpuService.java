package service.hardware;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

import java.util.concurrent.TimeUnit;

public class CpuService {
  private final CentralProcessor processor;
  private final Sensors sensors;

  public CpuService() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    this.processor = hal.getProcessor();
    this.sensors = hal.getSensors();
  }

  public String getCpuName() {
    return processor.getProcessorIdentifier().getName();
  }

  public int getLogicalCores() {
    return processor.getLogicalProcessorCount();
  }

  public int getPhysicalCores() {
    return processor.getPhysicalProcessorCount();
  }

  public String getArchitecture() {
    return processor.getProcessorIdentifier().getMicroarchitecture();
  }

  public long getFrequencyHz() {
    return processor.getProcessorIdentifier().getVendorFreq();
  }

  public double[] getCpuLoadPerCore(int intervalSeconds) throws InterruptedException {
    var prevTicks = processor.getProcessorCpuLoadTicks();
    TimeUnit.SECONDS.sleep(intervalSeconds);
    return processor.getProcessorCpuLoadBetweenTicks(prevTicks);
  }

  public double getCpuLoadTotal(int intervalSeconds) throws InterruptedException {
    var prevTicks = processor.getSystemCpuLoadTicks();
    TimeUnit.SECONDS.sleep(intervalSeconds);
    return processor.getSystemCpuLoadBetweenTicks(prevTicks);
  }

  public long[] getSystemCpuTicks() {
    return processor.getSystemCpuLoadTicks();
  }

  public double getCpuTemperature() {
    return sensors.getCpuTemperature(); // may return 0.0 if unsupported
  }
}
