package info.hardware.cpu;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

public class Cpu {
  private final CentralProcessor processor;
  private final Sensors sensors;

  public Cpu() {
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

  public double[] getCpuLoadPerCore() throws InterruptedException {
    var prevTicks = processor.getProcessorCpuLoadTicks();
    Thread.sleep(500);
    return processor.getProcessorCpuLoadBetweenTicks(prevTicks);
  }

  public double getCpuLoadTotal() throws InterruptedException {
    long[] prevTicks = processor.getSystemCpuLoadTicks();
    Thread.sleep(500);
    return processor.getSystemCpuLoadBetweenTicks(prevTicks);
  }

  public long[] getSystemCpuTicks() {
    return processor.getSystemCpuLoadTicks();
  }

  public double getCpuTemperature() {
    return sensors.getCpuTemperature(); // may return 0.0 if unsupported
  }

  public double cpuVoltage() {
    return sensors.getCpuVoltage();
  }

  public int[] fanSpeeds() {
    return sensors.getFanSpeeds();
  }
}
