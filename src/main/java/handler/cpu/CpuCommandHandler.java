package handler.cpu;

import command.cpu.CpuOutputOption;
import service.hardware.CpuService;

import java.util.*;
import java.util.stream.IntStream;

public class CpuCommandHandler {
  private final CpuService cpuService = new CpuService();

  private final Map<CpuOutputOption, Runnable> actions =
      Map.of(
          CpuOutputOption.INFO,
          this::printCpuInfo,
          CpuOutputOption.TEMPERATURE,
          this::printCpuTemperature,
          CpuOutputOption.TOTAL_LOAD,
          () -> printCpuLoad(true, false),
          CpuOutputOption.PER_CORE,
          () -> printCpuLoad(false, true));

  public void handle(Set<CpuOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printCpuInfo() {
    StringBuilder info = new StringBuilder("🧠 CPU Info:\n");
    info.append("Model:        ").append(cpuService.getCpuName()).append("\n");
    info.append("Architecture: ").append(cpuService.getArchitecture()).append("\n");
    info.append(
        String.format(
            "Cores:        %d physical / %d logical%n",
            cpuService.getPhysicalCores(), cpuService.getLogicalCores()));
    long freq = cpuService.getFrequencyHz();
    if (freq > 0) {
      info.append(String.format("Frequency:    %.2f GHz%n", freq / 1e9));
    }
    System.out.print(info);
  }

  private void printCpuTemperature() {
    double temp = cpuService.getCpuTemperature();
    String message =
        (temp > 0)
            ? String.format("🌡️ Temperature: %.1f °C%n", temp)
            : "🌡️ Temperature data not available.";
    System.out.print(message);
  }

  private void printCpuLoad(boolean total, boolean perCore) {
    System.out.print("⏱️ Measuring CPU load...\n");
    try {
      if (total) {
        System.out.printf("Total CPU Load: %.2f%%%n", cpuService.getCpuLoadTotal() * 100);
      }
      if (perCore) {
        double[] perCoreLoads = cpuService.getCpuLoadPerCore();
        System.out.println("Per-core CPU load:");
        IntStream.range(0, perCoreLoads.length)
            .mapToObj(i -> String.format("Core %d: %.2f%%", i, perCoreLoads[i] * 100))
            .forEach(System.out::println);
      }
    } catch (InterruptedException e) {
      System.err.println("❌ Interrupted while measuring CPU load.");
      Thread.currentThread().interrupt();
    }
  }
}
