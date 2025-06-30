package handler.ram;

import command.ram.MemoryOutputOption;
import info.hardware.ram.Memory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MemoryCommandHandler {
  private final Memory memory = new Memory();

  private final Map<MemoryOutputOption, Runnable> actions =
      Map.of(
          MemoryOutputOption.INFO, this::printMemoryInfo,
          MemoryOutputOption.SWAP, this::printSwapInfo,
          MemoryOutputOption.PAGE_SIZE, this::printPageSize);

  public void handle(Set<MemoryOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No memory options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printMemoryInfo() {
    System.out.println("🧠 Memory Info:");
    System.out.printf("Total Memory:     %.2f GB%n", memory.totalMemory() / 1e9);
    System.out.printf("Available Memory: %.2f GB%n", memory.availableMemory() / 1e9);
  }

  private void printSwapInfo() {
    System.out.println("💾 Swap Info:");
    System.out.printf("Total Swap: %.2f GB%n", memory.swapTotal() / 1e9);
    System.out.printf("Used Swap:  %.2f GB%n", memory.swapUsed() / 1e9);
  }

  private void printPageSize() {
    System.out.printf("📐 Page Size: %d bytes%n", memory.pageSize());
  }
}
