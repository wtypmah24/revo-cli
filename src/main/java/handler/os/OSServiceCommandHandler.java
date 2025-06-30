package handler.os;

import command.os.ServiceOutputOption;
import info.os.OSServiceInfo;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class OSServiceCommandHandler {
  private final OSServiceInfo serviceInfo = new OSServiceInfo();

  private final Map<ServiceOutputOption, Runnable> actions =
      Map.of(
          ServiceOutputOption.LIST, this::printServiceList,
          ServiceOutputOption.RUNNING, this::printRunningServices);

  public void handle(Set<ServiceOutputOption> selected) {
    if (selected == null || selected.isEmpty()) {
      System.out.println("⚠️ No service options specified. Use --help to see available flags.");
      return;
    }

    selected.stream().map(actions::get).filter(Objects::nonNull).forEach(Runnable::run);
  }

  private void printServiceList() {
    System.out.println("🛠️ All Services:");
    var names = serviceInfo.getNames();
    var states = serviceInfo.getStates();
    var pids = serviceInfo.getProcessIds();

    IntStream.range(0, names.size())
        .limit(15)
        .forEach(
            i -> {
              System.out.printf(
                  "• %s → State: %s | PID: %d%n", names.get(i), states.get(i), pids.get(i));
            });

    if (names.size() > 15) {
      System.out.printf("... and %d more%n", names.size() - 15);
    }
  }

  private void printRunningServices() {
    System.out.println("▶️ Running Services:");
    var names = serviceInfo.getNames();
    var states = serviceInfo.getStates();
    var pids = serviceInfo.getProcessIds();

    IntStream.range(0, names.size())
        .filter(i -> states.get(i).equalsIgnoreCase("RUNNING"))
        .forEach(
            i -> {
              System.out.printf("• %s | PID: %d%n", names.get(i), pids.get(i));
            });
  }
}
