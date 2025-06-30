package command.power;

import handler.power.PowerCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "power-command", description = "Show power source information")
public class PowerCommand implements Runnable {

  private final PowerCommandHandler handler = new PowerCommandHandler();

  @Option(names = "--info", description = "Show power source info")
  private boolean showInfo;

  @Option(names = "--status", description = "Show power status (charging, online, time remaining)")
  private boolean showStatus;

  @Option(names = "--usage", description = "Show power usage details (voltage, amperage, usage)")
  private boolean showUsage;

  @Override
  public void run() {
    EnumSet<PowerOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showInfo, PowerOutputOption.INFO),
                Map.entry(showStatus, PowerOutputOption.STATUS),
                Map.entry(showUsage, PowerOutputOption.USAGE))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(PowerOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
