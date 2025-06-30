package handler.process;

import command.process.ProcessOutputOption;
import info.os.OSProcessInfo;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class OSProcessCommandHandler {
  private final OSProcessInfo proc = new OSProcessInfo();

  private final Map<ProcessOutputOption, Runnable> actions =
      Map.of(
          ProcessOutputOption.LIST, this::printProcessList,
          ProcessOutputOption.MEMORY, this::printMemoryUsage,
          ProcessOutputOption.STATUS, this::printStatusInfo);

  public void handle(Set<ProcessOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No process options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printProcessList() {
    System.out.println("📋 Running Processes:");
    var ids = proc.getProcessIds();
    var names = proc.getProcessNames();
    var users = proc.getUsers();

    IntStream.range(0, ids.size())
        .limit(10)
        .forEach(
            i -> {
              System.out.printf(
                  "• PID: %d | Name: %s | User: %s%n", ids.get(i), names.get(i), users.get(i));
            });
    if (ids.size() > 10) {
      System.out.printf("... and %d more%n", ids.size() - 10);
    }
  }

  private void printMemoryUsage() {
    System.out.println("🧠 Memory Usage by Processes:");
    var names = proc.getProcessNames();
    var virt = proc.getVirtualMemory();
    var rss = proc.getResidentSetSize();

    IntStream.range(0, names.size())
        .limit(10)
        .forEach(
            i -> {
              System.out.printf(
                  "• %s → Virtual: %.2f MB, RSS: %.2f MB%n",
                  names.get(i), virt.get(i) / 1e6, rss.get(i) / 1e6);
            });
  }

  private void printStatusInfo() {
    System.out.println("📊 Process Status:");
    var ids = proc.getProcessIds();
    var names = proc.getProcessNames();
    var states = proc.getStates();
    var uptimes = proc.getUpTimes();
    var pids = proc.getParentProcessIds();

    IntStream.range(0, ids.size())
        .limit(10)
        .forEach(
            i -> {
              System.out.printf(
                  "• PID %d (%s) → State: %s | Uptime: %ds | Parent PID: %d%n",
                  ids.get(i), names.get(i), states.get(i), uptimes.get(i) / 1000, pids.get(i));
            });
  }
}
