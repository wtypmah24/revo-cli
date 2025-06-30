package command.hwd;

import handler.hwd.DiscCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "disc-command", description = "Show disk-related information")
public class DiscCommand implements Runnable {
  private final DiscCommandHandler handler = new DiscCommandHandler();

  @Option(names = "--info", description = "Show basic disk info (name, model, serial, size)")
  private boolean showInfo;

  @Option(names = "--read-write", description = "Show disk I/O statistics")
  private boolean showReadWrite;

  @Option(names = "--mounts", description = "Show filesystem mount points and space usage")
  private boolean showMountPoints;

  @Override
  public void run() {
    EnumSet<DiscOutputOption> selectedOptions =
        Stream.of(
                Map.entry(showInfo, DiscOutputOption.INFO),
                Map.entry(showReadWrite, DiscOutputOption.READ_WRITE),
                Map.entry(showMountPoints, DiscOutputOption.MOUNT_POINTS))
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(DiscOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
