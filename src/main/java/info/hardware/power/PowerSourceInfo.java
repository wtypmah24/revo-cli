package info.hardware.power;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

import java.util.List;

public class PowerSourceInfo {
  private final List<PowerSource> powerSourceInfos;

  public PowerSourceInfo() {
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    this.powerSourceInfos = hal.getPowerSources();
  }

  public List<String> getNames() {
    return powerSourceInfos.stream().map(PowerSource::getName).toList();
  }

  public List<String> getManufacturer() {
    return powerSourceInfos.stream().map(PowerSource::getManufacturer).toList();
  }

  public List<Double> getRemainingCapacities() {
    return powerSourceInfos.stream().map(PowerSource::getRemainingCapacityPercent).toList();
  }

  public List<Integer> getMaxCapacities() {
    return powerSourceInfos.stream().map(PowerSource::getMaxCapacity).toList();
  }

  public List<Integer> getCurrentCapacities() {
    return powerSourceInfos.stream().map(PowerSource::getCurrentCapacity).toList();
  }

  public List<Double> getVoltage() {
    return powerSourceInfos.stream().map(PowerSource::getVoltage).toList();
  }

  public List<Double> getAmperage() {
    return powerSourceInfos.stream().map(PowerSource::getAmperage).toList();
  }

  public List<Double> getPowerUsageRate() {
    return powerSourceInfos.stream().map(PowerSource::getPowerUsageRate).toList();
  }

  public List<Double> getTimeRemaining() {
    return powerSourceInfos.stream().map(PowerSource::getTimeRemainingEstimated).toList();
  }

  public List<Boolean> isCharging() {
    return powerSourceInfos.stream().map(PowerSource::isCharging).toList();
  }

  public List<Boolean> isPresent() {
    return powerSourceInfos.stream().map(PowerSource::isPowerOnLine).toList();
  }
}
