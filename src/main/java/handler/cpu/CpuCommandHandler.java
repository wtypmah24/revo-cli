package handler.cpu;

import command.cpu.CpuOutputOption;
import info.hardware.cpu.Cpu;

import java.util.*;
import java.util.stream.IntStream;

public class CpuCommandHandler {
  private final Cpu cpu = new Cpu();

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
    info.append("Model:        ").append(cpu.getCpuName()).append("\n");
    info.append("Architecture: ").append(cpu.getArchitecture()).append("\n");
    info.append(
        String.format(
            "Cores:        %d physical / %d logical%n",
            cpu.getPhysicalCores(), cpu.getLogicalCores()));
    long freq = cpu.getFrequencyHz();
    if (freq > 0) {
      info.append(String.format("Frequency:    %.2f GHz%n", freq / 1e9));
    }
    System.out.print(info);
  }

  private void printCpuTemperature() {
    double temp = cpu.getCpuTemperature();
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
        System.out.printf("Total CPU Load: %.2f%%%n", cpu.getCpuLoadTotal() * 100);
      }
      if (perCore) {
        double[] perCoreLoads = cpu.getCpuLoadPerCore();
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
