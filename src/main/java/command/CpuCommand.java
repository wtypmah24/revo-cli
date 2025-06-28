package command;

import handler.CpuCommandHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import service.hardware.CpuService;

@Command(name = "cpu-command", description = "Show cpu load")
public class CpuCommand implements Runnable {
  private final CpuService cpuService = new CpuService();
  private final CpuCommandHandler handler = new CpuCommandHandler(cpuService);

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
    handler.handle(showInfo, showTemperature, showTotalLoad, showPerCore);
  }
}
