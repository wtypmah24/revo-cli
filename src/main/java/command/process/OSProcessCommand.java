package command.process;

import handler.process.OSProcessCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "process-command", description = "Show OS process information")
public class OSProcessCommand implements Runnable {

  private final OSProcessCommandHandler handler = new OSProcessCommandHandler();

  @Option(names = "--list", description = "Show list of running processes")
  private boolean showList;

  @Option(names = "--memory", description = "Show memory usage of processes")
  private boolean showMemory;

  @Option(names = "--status", description = "Show process status info")
  private boolean showStatus;

  @Override
  public void run() {
    EnumSet<ProcessOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showList, ProcessOutputOption.LIST),
                Map.entry(showMemory, ProcessOutputOption.MEMORY),
                Map.entry(showStatus, ProcessOutputOption.STATUS))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(ProcessOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
