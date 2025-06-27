package command;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import service.CpuLoadService;

@Command(name = "cpu-command", description = "Show cpu load")
public class CpuCommand implements Runnable {
  private final CpuLoadService cpuLoadService = new CpuLoadService();

  @Option(names = "--interval", description = "Interval in seconds", defaultValue = "1")
  private int interval;

  public CpuCommand() {}

  @Override
  public void run() {
    try {
      double load = cpuLoadService.getAverageCpuLoad(interval);
      System.out.printf("CPU Load (%.1f sec): %.2f%%%n", (double) interval, load * 100);
    } catch (InterruptedException e) {
      System.err.println("Interrupted while measuring CPU load");
      Thread.currentThread().interrupt();
    }
  }
}
