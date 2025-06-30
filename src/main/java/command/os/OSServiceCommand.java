package command.os;

import handler.os.OSServiceCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "service-command", description = "Show OS services info")
public class OSServiceCommand implements Runnable {

  private final OSServiceCommandHandler handler = new OSServiceCommandHandler();

  @Option(names = "--list", description = "Show list of all services")
  private boolean showList;

  @Option(names = "--running", description = "Show only running services")
  private boolean showRunning;

  @Override
  public void run() {
    EnumSet<ServiceOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showList, ServiceOutputOption.LIST),
                Map.entry(showRunning, ServiceOutputOption.RUNNING))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(ServiceOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
