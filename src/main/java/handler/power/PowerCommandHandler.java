package handler.power;

import command.power.PowerOutputOption;
import info.hardware.power.PowerSourceInfo;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class PowerCommandHandler {
  private final PowerSourceInfo power = new PowerSourceInfo();

  private final Map<PowerOutputOption, Runnable> actions =
      Map.of(
          PowerOutputOption.INFO, this::printPowerInfo,
          PowerOutputOption.STATUS, this::printPowerStatus,
          PowerOutputOption.USAGE, this::printPowerUsage);

  public void handle(Set<PowerOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No power options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printPowerInfo() {
    System.out.println("🔋 Power Sources Info:");
    var names = power.getNames();
    var manufacturers = power.getManufacturer();
    var maxCaps = power.getMaxCapacities();
    var currCaps = power.getCurrentCapacities();
    var remaining = power.getRemainingCapacities();

    IntStream.range(0, names.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• %s (%s): %.0f%% (%d / %d mWh)%n",
                  names.get(i),
                  manufacturers.get(i),
                  remaining.get(i),
                  currCaps.get(i),
                  maxCaps.get(i));
            });
  }

  private void printPowerStatus() {
    System.out.println("⚡ Power State:");
    var charging = power.isCharging();
    var online = power.isPresent();
    var timeRemaining = power.getTimeRemaining();

    IntStream.range(0, charging.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Source %d: %s | Online: %s | Time left: %s%n",
                  i,
                  charging.get(i) ? "Charging" : "Discharging",
                  online.get(i) ? "Yes" : "No",
                  timeRemaining.get(i) > 0 ? formatMinutes(timeRemaining.get(i)) : "N/A");
            });
  }

  private void printPowerUsage() {
    System.out.println("📉 Power Usage:");
    var voltage = power.getVoltage();
    var amperage = power.getAmperage();
    var usageRate = power.getPowerUsageRate();

    IntStream.range(0, voltage.size())
        .forEach(
            i -> {
              System.out.printf(
                  "• Source %d: Voltage = %.2f V, Amperage = %.2f A, Usage = %.2f W%n",
                  i, voltage.get(i), amperage.get(i), usageRate.get(i));
            });
  }

  private String formatMinutes(double seconds) {
    int minutes = (int) Math.round(seconds / 60);
    return minutes + " min";
  }
}
