package command.ram;

import handler.ram.MemoryCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "memory-command", description = "Show memory information")
public class MemoryCommand implements Runnable {

  private final MemoryCommandHandler handler = new MemoryCommandHandler();

  @Option(names = "--info", description = "Show memory info (total and available)")
  private boolean showInfo;

  @Option(names = "--swap", description = "Show swap memory info")
  private boolean showSwap;

  @Option(names = "--page-size", description = "Show memory page size")
  private boolean showPageSize;

  @Override
  public void run() {
    EnumSet<MemoryOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showInfo, MemoryOutputOption.INFO),
                Map.entry(showSwap, MemoryOutputOption.SWAP),
                Map.entry(showPageSize, MemoryOutputOption.PAGE_SIZE))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(MemoryOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
