package command.cpu;

import handler.cpu.CpuCommandHandler;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cpu-command", description = "Show cpu load")
public class CpuCommand implements Runnable {

  private final CpuCommandHandler handler = new CpuCommandHandler();

  @Option(names = "--total-load", description = "Show total CPU load")
  private boolean showTotalLoad;

  @Option(names = "--per-core", description = "Show per-core CPU load")
  private boolean showPerCore;

  @Option(names = "--info", description = "Show CPU info (name, cores, arch, freq)")
  private boolean showInfo;

  @Option(names = "--temperature", description = "Show CPU temperature")
  private boolean showTemperature;

  @Override
  public void run() {
    EnumSet<CpuOutputOption> selectedOptions = Stream.of(
                    Map.entry(showInfo, CpuOutputOption.INFO),
                    Map.entry(showTemperature, CpuOutputOption.TEMPERATURE),
                    Map.entry(showTotalLoad, CpuOutputOption.TOTAL_LOAD),
                    Map.entry(showPerCore, CpuOutputOption.PER_CORE)
            )
            .filter(Map.Entry::getKey)
            .map(Map.Entry::getValue)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(CpuOutputOption.class)));

    handler.handle(selectedOptions);
  }
}
