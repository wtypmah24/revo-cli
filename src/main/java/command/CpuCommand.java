package command;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import service.hardware.CpuService;

@Command(name = "cpu-command", description = "Show cpu load")
public class CpuCommand implements Runnable {
  private final CpuService cpuService = new CpuService();

  @Option(names = "--interval", description = "Interval in seconds", defaultValue = "1")
  private int interval;

  @Override
  public void run() {
    try {
      double load = cpuService.getCpuLoadTotal(interval);
      System.out.printf("CPU Load (%.1f sec): %.2f%%%n", (double) interval, load * 100);
    } catch (InterruptedException e) {
      System.err.println("Interrupted while measuring CPU load");
      Thread.currentThread().interrupt();
    }
  }
}
